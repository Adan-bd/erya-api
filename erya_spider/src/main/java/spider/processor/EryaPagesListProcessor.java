package spider.processor;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import spider.mapper.AnswerTempMapper;
import spider.pojo.AnswerTemp;
import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.Site;
import us.codecraft.webmagic.Spider;
import us.codecraft.webmagic.processor.PageProcessor;
import us.codecraft.webmagic.scheduler.BloomFilterDuplicateRemover;
import us.codecraft.webmagic.scheduler.QueueScheduler;

import java.util.List;


@Component
public class EryaPagesListProcessor implements PageProcessor {
    private AnswerTempMapper answerTempMapper;
    private Logger logger = LoggerFactory.getLogger(EryaPagesListProcessor.class);

    @Autowired
    public EryaPagesListProcessor(AnswerTempMapper answerTempMapper) {
        this.answerTempMapper = answerTempMapper;
    }

    // 抓取网站的相关配置，可以包括编码、抓取间隔1s、重试次数等
    private Site site = Site.me()
            .setCharset("utf8")
            .setRetryTimes(5)
            .setTimeOut(10000)
            .setUseGzip(true)
            .setUserAgent("Mozilla/5.0 (Windows NT 10.0; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/74.0.3729.169 Safari/537.36");

    /**
     * private String getSubUtilSimple(String soap, String rgex) {
     * Pattern pattern = Pattern.compile(rgex);// 匹配的模式
     * Matcher m = pattern.matcher(soap);
     * if (m.find()) {
     * return m.group(1);
     * }
     * //(?<=<p>)[\s\S]*?(?=</p>)
     * return soap;
     * }
     */

    @Override
    public void process(Page page) {
        if (page.getHtml().css(".entry-content").all().size() == 0) {
            List<String> list = page.getHtml().css(".entry-title").links().all();
            for (String s : list) {
                page.addTargetRequest(s);
            }
        } else {
//            String question = page.getHtml().css(".entry-title", "text").all().get(0);
            String tmp = page.getHtml().css(".entry-title").all().get(0);
            String question = null;
            if (tmp != null) {
                question = tmp.substring(24, tmp.length() - 5);
            }
            StringBuilder answer = new StringBuilder();
            List<String> text = page.getHtml().css(".entry-content p", "text").all();
            for (String s : text) {
                answer.append(s).append("\n");
            }
            if (question != null && answer.length() != 0) {
                AnswerTemp answer1 = new AnswerTemp();
                answer1.setQuestion(question);
                answer1.setAnswer(answer.toString());
                page.putField("answer", answer1);
            }
        }
        List<String> res = page.getHtml().css(".next").links().all();
        if (res.size() != 0) {
            page.addTargetRequest(res.get(0));
        }
    }

    @Override
    public Site getSite() {
        return site;
    }

    public void start(String url, int thread, int sleep) {
        site.setSleepTime(sleep);
        Spider.create(this)
                .setScheduler(new QueueScheduler().setDuplicateRemover(new BloomFilterDuplicateRemover(100000)))
                .addPipeline((resultItems, task) -> {
                    AnswerTemp answer = resultItems.get("answer");
                    if (answer != null) {
                        logger.info(answer.toString());
                        answerTempMapper.insert(answer);
                    }
                })
                .addUrl(url)
                .thread(thread)
                .run();
    }
}
