package spider.service.imp;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import spider.pojo.AnswerTemp;
import spider.service.SimpleSpiderService;
import spider.vo.Han8;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

@Service
public class Han8Imp implements SimpleSpiderService {
    private RestTemplate restTemplate;
    private Logger logger = LoggerFactory.getLogger(Han8Imp.class);

    public Han8Imp(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    @Override
    public Set<AnswerTemp> findAnswer(String question) {
        String url = "https://api.han8.net/api/ti.ku?key=wym520&ti=";
        Han8 han8 = restTemplate.getForObject(url + question, Han8.class);
        logger.info(Objects.requireNonNull(han8).toString());
        Set<AnswerTemp> answerTemps = new HashSet<>();
        if (!han8.getAnswer().equals("未找到答案，请联系客服QQ1655466387")) {
            AnswerTemp answerTemp = new AnswerTemp();
            answerTemp.setQuestion(han8.getProblem());
            answerTemp.setAnswer(han8.getAnswer());
            answerTemps.add(answerTemp);
        }
        return answerTemps;
    }
}
