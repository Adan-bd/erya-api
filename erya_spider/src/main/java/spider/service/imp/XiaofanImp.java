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
import spider.vo.Xiaofan;

import java.util.*;

@Service
public class XiaofanImp implements SimpleSpiderService {
    private RestTemplate restTemplate;
    private Logger logger = LoggerFactory.getLogger(XiaofanImp.class);

    public XiaofanImp(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    @Override
    public Set<AnswerTemp> findAnswer(String question) {
        List<String> list = new ArrayList<>();
        list.add("https://xiaofan-tk.cn/xiaofan/chaoxing/chaoxing.php");
        MultiValueMap<String, Object> postParameters = new LinkedMultiValueMap<>();
        postParameters.add("data", question);
        postParameters.add("time", System.currentTimeMillis());
        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Type", "application/x-www-form-urlencoded");
        List<String> tokens = new ArrayList<>();
        tokens.add("11903b517eaff96f5770729cac58a4c5");
        tokens.add("899207f2ea9b36548b6d16eab6e5508a");
        Set<AnswerTemp> answerTemps = new HashSet<>();
        for (String token : tokens) {
            postParameters.add("token", token);
            HttpEntity<MultiValueMap<String, Object>> r = new HttpEntity<>(postParameters, headers);
            for (String url : list) {
                Xiaofan xiaofan = restTemplate.postForObject(url, r, Xiaofan.class);
                logger.info(Objects.requireNonNull(xiaofan).toString());
                if (xiaofan.getAnswer() != null && !xiaofan.getQuestion().equals("此题目暂未找到答案。")) {
                    AnswerTemp answerTemp = new AnswerTemp();
                    answerTemp.setQuestion(xiaofan.getQuestion());
                    answerTemp.setAnswer(xiaofan.getAnswer());
                    answerTemps.add(answerTemp);
                }
            }
        }
        return answerTemps;
    }
}
