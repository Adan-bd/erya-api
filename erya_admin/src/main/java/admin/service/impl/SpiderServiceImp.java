package admin.service.impl;


import admin.mapper.AnswerMapper;
import admin.mapper.SpiderMapper;
import admin.pojo.Spider;
import admin.service.SpiderService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@Service
public class SpiderServiceImp implements SpiderService {
    private SpiderMapper spiderMapper;
    private AnswerMapper answerMapper;
    private RestTemplate restTemplate;

    public SpiderServiceImp(SpiderMapper spiderMapper, AnswerMapper answerMapper, RestTemplate restTemplate) {
        this.spiderMapper = spiderMapper;
        this.answerMapper = answerMapper;
        this.restTemplate = restTemplate;
    }

    @Override
    public IPage<Spider> selectSpider(int pageNo, int pageSize, String search) {
        IPage<Spider> page = new Page<>(pageNo, pageSize);
        QueryWrapper<Spider> wrapper = new QueryWrapper<>();
        wrapper.like("question", "%" + search + "%");
        return spiderMapper.selectPage(page, wrapper);
    }

    @Override
    public int deleteSpider(List<String> list) {
        int res = 0;
        for (String integer : list) {
            res += spiderMapper.deleteById(integer);
        }
        return res;
    }

    @Override
    public int modifySpider(Spider spider) {
        return spiderMapper.updateById(spider);
    }

    @Transactional
    @Override
    public int mergeAll() {
        int res = 0;
        List<Spider> list = spiderMapper.selectList(null);
        for (Spider spider : list) {
            res += answerMapper.insertBySpider(spider.getQuestion(), spider.getAnswer());
        }
        spiderMapper.delete(null);
        return res;
    }

    @Override
    public int merge(List<Integer> list) {
        int res = 0;
        for (Integer integer : list) {
            Spider spider = spiderMapper.selectById(integer);
            res += answerMapper.insertBySpider(spider.getQuestion(), spider.getAnswer());
            spiderMapper.deleteById(spider.getId());
        }
        return res;
    }

    @Override
    public void start(String url, int thread, int sleep) {
        MultiValueMap<String, Object> postParameters = new LinkedMultiValueMap<>();
        postParameters.add("url", url);
        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Type", "application/x-www-form-urlencoded");
        HttpEntity<MultiValueMap<String, Object>> r = new HttpEntity<>(postParameters, headers);
        restTemplate.postForObject("https://spider.erya.ychstudy.cn/spider/"+thread+"/"+sleep, r, Void.class);
    }
}
