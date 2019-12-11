package admin.service;


import admin.pojo.Spider;
import com.baomidou.mybatisplus.core.metadata.IPage;

import java.util.List;

public interface SpiderService {
    IPage<Spider> selectSpider(int page, int pageSize, String search);

    int deleteSpider(List<String> answers);

    int modifySpider(Spider spider);

    int mergeAll();

    int merge(List<Integer> list);

    void start(String url, int thread, int sleep);
}
