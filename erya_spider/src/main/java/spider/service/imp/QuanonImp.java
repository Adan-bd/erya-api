package spider.service.imp;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import spider.pojo.AnswerTemp;
import spider.service.SimpleSpiderService;
import spider.vo.Quanon;

import java.io.IOException;
import java.util.*;

@Service
public class QuanonImp implements SimpleSpiderService {
    private RestTemplate restTemplate;
    private Logger logger = LoggerFactory.getLogger(QuanonImp.class);

    public QuanonImp(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    @Override
    public Set<AnswerTemp> findAnswer(String question) {
        List<String> list = new ArrayList<>();
        list.add("https://wx.quanon.cn/miniProgram/chatiindex.php/");
        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Type", "application/json");
        HttpEntity<String> r = new HttpEntity<>("{\"question\":\"" + question + "\"}", headers);
        ObjectMapper objectMapper = new ObjectMapper();
        Set<AnswerTemp> answerTemps = new HashSet<>();
        for (String url : list) {
            String s = restTemplate.postForObject(url, r, String.class);
            Quanon quanon = null;
            try {
                quanon = objectMapper.readValue(s, Quanon.class);
            } catch (IOException e) {
                e.printStackTrace();
            }
            logger.info(Objects.requireNonNull(quanon).toString());
            if (quanon.getQuestion() != null && !quanon.getQuestion().equals("")) {
                AnswerTemp answerTemp = new AnswerTemp();
                answerTemp.setQuestion(quanon.getQuestion());
                answerTemp.setAnswer(quanon.getAnswer());
                answerTemps.add(answerTemp);
            }
        }
        return answerTemps;
    }
}
