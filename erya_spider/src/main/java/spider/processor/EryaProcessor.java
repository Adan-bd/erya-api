package spider.processor;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import spider.mapper.SpiderMapper;
import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.Site;
import us.codecraft.webmagic.Spider;
import us.codecraft.webmagic.processor.PageProcessor;
import us.codecraft.webmagic.scheduler.BloomFilterDuplicateRemover;
import us.codecraft.webmagic.scheduler.QueueScheduler;

import java.util.ArrayList;
import java.util.List;


@Component
public class EryaProcessor implements PageProcessor {
    private SpiderMapper spiderMapper;
    private Logger logger = LoggerFactory.getLogger(EryaProcessor.class);

    @Autowired
    public EryaProcessor(SpiderMapper spiderMapper) {
        this.spiderMapper = spiderMapper;
    }

    // 抓取网站的相关配置，可以包括编码、抓取间隔1s、重试次数等
    private Site site = Site.me()
            .setCharset("utf8")
            .setRetryTimes(5)
            .setTimeOut(60000)
            .setUseGzip(true)
            .setUserAgent("Mozilla/5.0 (Windows NT 10.0; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/74.0.3729.169 Safari/537.36");

    @Override
    public void process(Page page) {
        List<String> res;
        List<String> request = new ArrayList<>();
        request.add(".nav-previous");
        request.add(".nav-next");
        for (String s : request) {
            res = page.getHtml().css(s).links().all();
            if (res.size() != 0) {
                page.addTargetRequest(res.get(0));
            }
        }
//        String question = page.getHtml().css(".entry-title", "text").all().get(0);
//        String answer;
//        if (page.getHtml().css(".entry-content br").all().size() <= 4) {
//            answer = page.getHtml().css(".entry-content p", "text").all().get(1);
//        } else {
//            String[] temp = page.getHtml().css(".entry-content p").all().get(0).split("<br>");
//            answer = temp[temp.length - 1].split("<p>")[0].split("</p>")[0];
//        }
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
            spider.pojo.Spider answer1 = new spider.pojo.Spider();
            answer1.setQuestion(question);
            answer1.setAnswer(answer.toString());
            logger.info(answer1.toString());
            page.putField("answer", answer1);
        }
    }

    @Override
    public Site getSite() {
        return site;
    }

    public void start(String url, int thread, int sleep) {
        site.setSleepTime(sleep);
        Spider.create(this)
                .setScheduler(new QueueScheduler().setDuplicateRemover(new BloomFilterDuplicateRemover(1000000)))
                .addPipeline((resultItems, task) -> {
                    spider.pojo.Spider answer = resultItems.get("answer");
                    if (answer != null) {
                        spiderMapper.insert(answer);
                    }
                })
                .addUrl(url)
                .thread(thread)
                .run();
    }
}
