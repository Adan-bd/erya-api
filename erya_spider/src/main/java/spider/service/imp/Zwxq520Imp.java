package spider.service.imp;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import spider.mapper.CourseDetailMapper;
import spider.pojo.AnswerTemp;
import spider.pojo.CourseDetail;
import spider.service.SimpleSpiderService;
import spider.vo.Zwxq520;
import spider.vo.Zwxq520Child;

import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;

@Service
public class Zwxq520Imp implements SimpleSpiderService {
    private CourseDetailMapper courseDetailMapper;
    private RestTemplate restTemplate;
    private Logger logger = LoggerFactory.getLogger(Zwxq520Imp.class);

    public Zwxq520Imp(CourseDetailMapper courseDetailMapper, RestTemplate restTemplate) {
        this.courseDetailMapper = courseDetailMapper;
        this.restTemplate = restTemplate;
    }

    @Override
    public Set<AnswerTemp> findAnswer(String question) {
        String url = "https://www.zwxq520.com/solr/search?param=";
        Zwxq520 zwxq520 = restTemplate.getForObject(url + question, Zwxq520.class);
        logger.info(Objects.requireNonNull(zwxq520).toString());
        Set<AnswerTemp> answerTemps = new HashSet<>();
        if (zwxq520.getData().size() != 0) {
            List<Zwxq520Child> data = zwxq520.getData();
            AnswerTemp answerTemp = new AnswerTemp();
            CourseDetail courseDetail = new CourseDetail();
            for (Zwxq520Child datum : data) {
                answerTemp.setQuestion("(" + datum.getSubjectName() + ")" + datum.getQuestion());
                answerTemp.setAnswer(datum.getAnswerStr() + "\n" + datum.getAnswers().toString());
                answerTemps.add(answerTemp);
                courseDetail.setAnswer(datum.getAnswerStr() + "\n" + datum.getAnswers().toString());
                courseDetail.setName(datum.getSubjectName());
                courseDetail.setQuestion(datum.getQuestion());
                courseDetailMapper.insert(courseDetail);
            }
        }
        return answerTemps;
    }
}
