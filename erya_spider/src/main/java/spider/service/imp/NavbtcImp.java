package spider.service.imp;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import spider.pojo.AnswerTemp;
import spider.service.SimpleSpiderService;
import spider.vo.Navbtc;

import java.util.*;

@Service
public class NavbtcImp implements SimpleSpiderService {
    private RestTemplate restTemplate;
    private Logger logger = LoggerFactory.getLogger(NavbtcImp.class);

    public NavbtcImp(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    @Override
    public Set<AnswerTemp> findAnswer(String question) {
        List<String> list = new ArrayList<>();
        list.add("https://www.navbtc.com/hk/think/public/index.php/index/login/mirror?w=");
        list.add("https://xiaohua.eastax.cn/wkjie/jiek.php?url=");
        Set<AnswerTemp> answerTemps = new HashSet<>();
        for (String url : list) {
            Navbtc navbtc = restTemplate.getForObject(url + question, Navbtc.class);
            logger.info(Objects.requireNonNull(navbtc).toString());
            String info = navbtc.getData().getInfo();
            int index = info.indexOf("答案：");
            if (index != -1) {
                AnswerTemp answerTemp = new AnswerTemp();
                answerTemp.setQuestion(info.substring(0, index));
                answerTemp.setAnswer(info.substring(index));
                answerTemps.add(answerTemp);
            }
        }
        return answerTemps;
    }
}
