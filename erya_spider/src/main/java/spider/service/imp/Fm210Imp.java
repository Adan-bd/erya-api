package spider.service.imp;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import spider.pojo.AnswerTemp;
import spider.service.SimpleSpiderService;

import java.util.HashSet;
import java.util.Set;

@Service
public class Fm210Imp implements SimpleSpiderService {
    private RestTemplate restTemplate;
    private Logger logger = LoggerFactory.getLogger(Fm210Imp.class);

    public Fm210Imp(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    @Override
    public Set<AnswerTemp> findAnswer(String question) {
        String url = "https://jk.fm210.cn/wangke/anci/yh.php?yhm=xingjiakeji&w=";
        String string = restTemplate.getForObject(url + question, String.class);
        logger.info(string);
        Set<AnswerTemp> answerTemps = new HashSet<>();
        if (string != null) {
            int index = string.indexOf("答案:");
            if (index != -1) {
                AnswerTemp answerTemp = new AnswerTemp();
                answerTemp.setQuestion(string.substring(0, index));
                answerTemp.setAnswer(string.substring(index));
                answerTemps.add(answerTemp);
            }
        }

        return answerTemps;
    }
}
