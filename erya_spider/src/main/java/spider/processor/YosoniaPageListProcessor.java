package spider.processor;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import spider.mapper.TempUrlMapper;
import spider.pojo.TempUrl;
import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.Site;
import us.codecraft.webmagic.Spider;
import us.codecraft.webmagic.processor.PageProcessor;
import us.codecraft.webmagic.scheduler.BloomFilterDuplicateRemover;
import us.codecraft.webmagic.scheduler.QueueScheduler;
import us.codecraft.webmagic.selector.Html;
import us.codecraft.webmagic.selector.Selectable;

import java.util.ArrayList;
import java.util.List;

@Component
public class YosoniaPageListProcessor implements PageProcessor {
    private TempUrlMapper tempUrlMapper;
    private Logger logger = LoggerFactory.getLogger(YosoniaPageListProcessor.class);

    public YosoniaPageListProcessor(TempUrlMapper tempUrlMapper) {
        this.tempUrlMapper = tempUrlMapper;
    }

    // 抓取网站的相关配置，可以包括编码、抓取间隔1s、重试次数等
    private Site site = Site.me()
            .setCharset("utf8")
            .setRetryTimes(5)
            .setTimeOut(10000)
            .setUseGzip(true)
            .setUserAgent("Mozilla/5.0 (Windows NT 10.0; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/74.0.3729.169 Safari/537.36");

    @Override
    public void process(Page page) {
        List<TempUrl> tempUrls = new ArrayList<>();
        Html html = page.getHtml();
        Selectable css = html.css(".mdui-text-color-theme");
        List<String> all = css.links().all();
        all.forEach(s -> {
            TempUrl tempUrl = new TempUrl();
            tempUrl.setUrl(s);
            tempUrls.add(tempUrl);
        });
        page.putField("temp_urls", tempUrls);
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
                    List<TempUrl> tempUrls = resultItems.get("temp_urls");
                    if (tempUrls.size() != 0) {
                        tempUrls.forEach(tempUrl -> {
                            logger.info(tempUrl.toString());
                            tempUrlMapper.insert(tempUrl);
                        });
                    }
                })
                .addUrl(url)
                .thread(thread)
                .run();
    }
}
