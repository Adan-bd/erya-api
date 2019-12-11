package spider.service.imp;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import spider.pojo.AnswerTemp;
import spider.service.SimpleSpiderService;

import java.io.IOException;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

@Service
public class Xuanxiu365Imp implements SimpleSpiderService {
    private Logger logger = LoggerFactory.getLogger(Xuanxiu365Imp.class);


    @Override
    public Set<AnswerTemp> findAnswer(String question) {
        String url = "https://chengxu.xuanxiu365.com/index.php?q=";
        Document document = null;
        try {
            document = Jsoup.connect(url + question).get();
        } catch (IOException e) {
            e.printStackTrace();
        }
        Objects.requireNonNull(document);
        Elements form = document.select("form");
        logger.info(form.toString());
        String ques = form.select(".weui-panel__hd").text();
        String answer = form.select(".weui-media-box__title").text();
        Set<AnswerTemp> answerTemps = new HashSet<>();
        if (!StringUtils.isEmpty(ques) && !StringUtils.isEmpty(answer)) {
            AnswerTemp answerTemp = new AnswerTemp();
            answerTemp.setQuestion(ques);
            answerTemp.setAnswer(answer);
            answerTemps.add(answerTemp);
        }
        return answerTemps;
    }
}
