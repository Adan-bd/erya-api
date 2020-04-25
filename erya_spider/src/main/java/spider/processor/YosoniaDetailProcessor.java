package spider.processor;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import spider.mapper.TempCourseMapper;
import spider.pojo.TempCourse;
import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.Site;
import us.codecraft.webmagic.Spider;
import us.codecraft.webmagic.processor.PageProcessor;
import us.codecraft.webmagic.scheduler.BloomFilterDuplicateRemover;
import us.codecraft.webmagic.scheduler.QueueScheduler;
import us.codecraft.webmagic.selector.Html;
import us.codecraft.webmagic.selector.Selectable;

import java.util.List;

@Component
public class YosoniaDetailProcessor implements PageProcessor {
    private TempCourseMapper tempCourseMapper;
    private Logger logger = LoggerFactory.getLogger(YosoniaDetailProcessor.class);

    // 抓取网站的相关配置，可以包括编码、抓取间隔1s、重试次数等
    private Site site = Site.me()
            .setCharset("utf8")
            .setRetryTimes(5)
            .setTimeOut(10000)
            .setUseGzip(true)
            .setUserAgent("Mozilla/5.0 (Windows NT 10.0; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/74.0.3729.169 Safari/537.36");

    public YosoniaDetailProcessor(TempCourseMapper tempCourseMapper) {
        this.tempCourseMapper = tempCourseMapper;
    }

    @Override
    public void process(Page page) {
        String url = page.getRequest().getUrl();
        String title = url.substring(url.lastIndexOf('/') + 1, url.lastIndexOf('.'));
        TempCourse tempCourse = new TempCourse();
        Html html = page.getHtml();
        Selectable css = html.css(".mdui-center", "text");
        List<String> all = css.all();
        tempCourse.setTitle(all.get(0));
        Selectable css1 = html.css(".post-" + title);
        Selectable p = css1.css("p");
        List<String> all1 = p.all();
        StringBuilder stringBuilder = new StringBuilder();
        all1.forEach(s -> {
            stringBuilder.append("\n");
            stringBuilder.append(s);
        });
        tempCourse.setContent(stringBuilder.toString());
        System.out.println(tempCourse);
        page.putField("temp_course", tempCourse);
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
                    TempCourse tempCourse = resultItems.get("temp_course");
                    if (tempCourse != null) {
                        logger.info(tempCourse.toString());
                        tempCourseMapper.insert(tempCourse);
                    }
                })
                .addUrl(url)
                .thread(thread)
                .run();
    }
}
