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
import spider.vo.ChaoXing360;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

@Service
public class Chaoxing360Imp implements SimpleSpiderService {
    private RestTemplate restTemplate;
    private Logger logger = LoggerFactory.getLogger(Chaoxing360Imp.class);

    public Chaoxing360Imp(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    @Override
    public Set<AnswerTemp> findAnswer(String question) {
        String url = "https://api.chaoxing360.com/exam/search/question/token/d67b8ee7c02111e9a2990235d2b38928";
        MultiValueMap<String, Object> postParameters = new LinkedMultiValueMap<>();
        postParameters.add("question", question);
        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Type", "application/x-www-form-urlencoded");
        HttpEntity<MultiValueMap<String, Object>> r = new HttpEntity<>(postParameters, headers);
        ChaoXing360 chaoxing360 = restTemplate.postForObject(url, r, ChaoXing360.class);
        logger.info(Objects.requireNonNull(chaoxing360).toString());
        Set<AnswerTemp> answerTemps = new HashSet<>();
        if (chaoxing360.getData().getAnswer() != null) {
            AnswerTemp answerTemp = new AnswerTemp();
            answerTemp.setQuestion(chaoxing360.getData().getQuestion());
            answerTemp.setAnswer(chaoxing360.getData().getAnswer());
            answerTemps.add(answerTemp);
        }
        return answerTemps;
    }
}
