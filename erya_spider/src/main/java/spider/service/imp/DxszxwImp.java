package spider.service.imp;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import spider.pojo.AnswerTemp;
import spider.service.SimpleSpiderService;
import spider.vo.Dxszxw;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

@Service
public class DxszxwImp implements SimpleSpiderService {
    private RestTemplate restTemplate;
    private Logger logger = LoggerFactory.getLogger(DxszxwImp.class);

    public DxszxwImp(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    @Override
    public Set<AnswerTemp> findAnswer(String question) {
        String url = "https://www.dxszxw.cn/wangke/search/?ques=";
        Dxszxw dxszxw = restTemplate.getForObject(url + question, Dxszxw.class);
        logger.info(Objects.requireNonNull(dxszxw).toString());
        Set<AnswerTemp> answerTemps = new HashSet<>();
        if (!dxszxw.getDaan().equals("服务器有点繁忙\uD83D\uDE30\uD83D\uDE30\uD83D\uDE30，稍后再试试呀")) {
            AnswerTemp answerTemp = new AnswerTemp();
            answerTemp.setQuestion(dxszxw.getTimu());
            answerTemp.setAnswer(dxszxw.getDaan());
            answerTemps.add(answerTemp);
        }
        return answerTemps;
    }
}
