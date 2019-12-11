package spider.service.imp;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import spider.pojo.AnswerTemp;
import spider.service.SimpleSpiderService;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
public class NshqappImp implements SimpleSpiderService {
    private RestTemplate restTemplate;
    private Logger logger = LoggerFactory.getLogger(NshqappImp.class);

    public NshqappImp(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    @Override
    public Set<AnswerTemp> findAnswer(String question) {
        List<String> list = new ArrayList<>();
        list.add("https://xcx.nshqapp.cn/xcx/eryachaxun?token=ozfMc5CIzteL-k9G5CcwSmoDE7OE&q=");
        list.add("https://xcx.nshqapp.cn/xcx/eryachaxun?token=oio5O5eYB27pfAwuNeuC5Icl12GM&q=");
        Set<AnswerTemp> answerTemps = new HashSet<>();
        for (String url : list) {
            String string = restTemplate.getForObject(url + question, String.class);
            logger.info(string);
            if (string != null) {
                int index = string.indexOf("答：");
                if (index != -1) {
                    AnswerTemp answerTemp = new AnswerTemp();
                    answerTemp.setQuestion(string.substring(0, index));
                    answerTemp.setAnswer(string.substring(index));
                    answerTemps.add(answerTemp);
                }
            }
        }
        return answerTemps;
    }
}
