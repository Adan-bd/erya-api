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
import spider.vo.XueXiaoYi;

import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;

@Service
public class XueXiaoYiImp implements SimpleSpiderService {
    private RestTemplate restTemplate;
    private Logger logger = LoggerFactory.getLogger(Chaoxing360Imp.class);
    private final String ans = "找不到结果，很可能是你输入的内容与题目不一致，请只输入题干部分，不能有多余的字、题目选项和错别字！！！如未收录请过几天再尝试，题目每天更新！";

    public XueXiaoYiImp(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    @Override
    public Set<AnswerTemp> findAnswer(String question) {
        String url = "https://app.51xuexiaoyi.com/api/v1/searchQuestion?keyword=" + question;
        HttpHeaders headers = new HttpHeaders();
        headers.add("token", "fYmjMJjna42PBWod6LNPGDSunXYC9t2oDkLS1Tth7NOYNVOqpBkiCoKWXzUN");
        HttpEntity<String> r = new HttpEntity<>(headers);
        Set<AnswerTemp> answerTemps = new HashSet<>();
        XueXiaoYi xuexiaoyi = restTemplate.postForObject(url, r, XueXiaoYi.class);
        logger.info(Objects.requireNonNull(xuexiaoyi).toString());
        List<XueXiaoYi.XuexiaoyiQuestion> data = xuexiaoyi.getData();
        if(data==null){
            return answerTemps;
        }
        data.forEach(xuexiaoyiQuestion -> {
            if (!xuexiaoyiQuestion.getQ().equals(ans)) {
                AnswerTemp answerTemp = new AnswerTemp();
                answerTemp.setQuestion(xuexiaoyiQuestion.getQ());
                answerTemp.setAnswer(xuexiaoyiQuestion.getA());
                answerTemps.add(answerTemp);
            }
        });
        return answerTemps;
    }
}
