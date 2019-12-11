package spider.service.imp;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;
import spider.pojo.AnswerTemp;
import spider.service.SimpleSpiderService;
import spider.vo.S599595;

import java.util.*;

@Service
public class S599595Imp implements SimpleSpiderService {
    private RestTemplate restTemplate;
    private Logger logger = LoggerFactory.getLogger(S599595Imp.class);

    public S599595Imp(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    @Override
    public Set<AnswerTemp> findAnswer(String question) {
        List<String> list = new ArrayList<>();
        list.add("https://599595.cn/chatidata.php");
        list.add("https://599595.cn/mkgData.php");
        MultiValueMap<String, Object> postParameters = new LinkedMultiValueMap<>();
        postParameters.add("content", question);
        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Type", "application/x-www-form-urlencoded");
        HttpEntity<MultiValueMap<String, Object>> r = new HttpEntity<>(postParameters, headers);
        Set<AnswerTemp> answerTemps = new HashSet<>();
        for (String url : list) {
            S599595 s599595 = restTemplate.postForObject(url, r, S599595.class);
            logger.info(Objects.requireNonNull(s599595).toString());
            if (!s599595.getContent().equals("未找到,请只输入题目或换个关键字！")) {
                AnswerTemp answerTemp = new AnswerTemp();
                answerTemp.setQuestion(s599595.getTitle());
                answerTemp.setAnswer(s599595.getContent());
                answerTemps.add(answerTemp);
            }
        }
        return answerTemps;
    }
}
