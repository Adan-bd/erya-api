package spider.service.imp;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import spider.pojo.AnswerTemp;
import spider.service.SimpleSpiderService;
import spider.vo.Yosonia;

import java.util.HashSet;
import java.util.Set;

@Service
public class YosoniaImp implements SimpleSpiderService {
    private RestTemplate restTemplate;
    private Logger logger = LoggerFactory.getLogger(YosoniaImp.class);

    public YosoniaImp(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    @Override
    public Set<AnswerTemp> findAnswer(String question) {
        String url = "https://www.yosonia.cn/ss/search.php?id=";
        Yosonia[] list = restTemplate.getForObject(url + question, Yosonia[].class);
        Set<AnswerTemp> answerTemps = new HashSet<>();
        if (list != null) {
            for (Yosonia yosonia : list) {
                if (yosonia != null) {
                    logger.info(yosonia.toString());
                    AnswerTemp answerTemp = new AnswerTemp();
                    answerTemp.setQuestion(yosonia.getId());
                    answerTemp.setAnswer(yosonia.getTitle());
                    answerTemps.add(answerTemp);
                }
            }
        }
        return answerTemps;
    }
}
