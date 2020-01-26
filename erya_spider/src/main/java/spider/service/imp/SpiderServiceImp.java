package spider.service.imp;

import common.vo.Questions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.stereotype.Service;
import spider.mapper.AnswerTempMapper;
import spider.pojo.AnswerTemp;
import spider.processor.EryaPagesListProcessor;
import spider.service.SimpleSpiderService;
import spider.service.SpiderService;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

@Service
public class SpiderServiceImp implements SpiderService {
    private List<SimpleSpiderService> list = new ArrayList<>();
    private AnswerTempMapper answerTempMapper;
    private Logger logger = LoggerFactory.getLogger(SpiderServiceImp.class);
    private EryaPagesListProcessor eryaPagesListProcessor;
    private ThreadPoolTaskExecutor taskExecutor;
    private RabbitTemplate rabbitTemplate;

    public SpiderServiceImp(AnswerTempMapper answerTempMapper, EryaPagesListProcessor eryaPagesListProcessor, ThreadPoolTaskExecutor taskExecutor, RabbitTemplate rabbitTemplate) {
        this.answerTempMapper = answerTempMapper;
        this.eryaPagesListProcessor = eryaPagesListProcessor;
        this.taskExecutor = taskExecutor;
        this.rabbitTemplate = rabbitTemplate;
    }

    @Autowired
    public void setChaoxing360Imp(Chaoxing360Imp chaoxing360Imp) {
        list.add(chaoxing360Imp);
    }

    @Autowired
    public void setDxszxwImp(DxszxwImp dxszxwImp) {
        list.add(dxszxwImp);
    }

    @Autowired
    public void setFm210Imp(Fm210Imp fm210Imp) {
        list.add(fm210Imp);
    }

    @Autowired
    public void setHan8Imp(Han8Imp han8Imp) {
        list.add(han8Imp);
    }

//    @Autowired
//    public void setNavbtcImp(NavbtcImp navbtcImp) {
//        list.add(navbtcImp);
//    }

//    @Autowired
//    public void setNshqappImp(NshqappImp nshqappImp) {
//        list.add(nshqappImp);
//    }

//    @Autowired
//    public void setQuanonImp(QuanonImp quanonImp) {
//        list.add(quanonImp);
//    }

//    @Autowired
//    public void setS599595Imp(S599595Imp s599595Imp) {
//        list.add(s599595Imp);
//    }

    @Autowired
    public void setXiaofanImp(XiaofanImp xiaofanImp) {
        list.add(xiaofanImp);
    }

    @Autowired
    public void setYosoniaImp(YosoniaImp yosoniaImp) {
        list.add(yosoniaImp);
    }

//    @Autowired
//    public void setZwxq520Imp(Zwxq520Imp zwxq520Imp) {
//        list.add(zwxq520Imp);
//    }

    @Autowired
    public void setXuanxiu365Imp(Xuanxiu365Imp xuanxiu365Imp) {
        list.add(xuanxiu365Imp);
    }

    public Set<AnswerTemp> findAnswer(String question) {
        String url = "https://www.yosonia.cn/page/1?s=";
        eryaPagesListProcessor.start(url + question, 1, 3000);
        List<Future<Set<AnswerTemp>>> lists = new ArrayList<>(list.size());
        for (SimpleSpiderService simpleSpiderService : list) {
            lists.add(taskExecutor.submit(new myThread(simpleSpiderService, question)));
        }
        Set<AnswerTemp> set = new HashSet<>(lists.size());
        for (Future<Set<AnswerTemp>> setFuture : lists) {
            try {
                set.addAll(setFuture.get());
            } catch (InterruptedException | ExecutionException e) {
                e.printStackTrace();
            }
        }
        logger.info(set.toString());
        for (AnswerTemp answerTemp : set) {
            answerTempMapper.insert(answerTemp);
        }
        return set;
    }

    @Override
    public void findAnswer(Questions spiderQuestion) {
        List<String> list = new ArrayList<>();
        List<String> questions = spiderQuestion.getQuestions();
        for (String question : questions) {
            Set<AnswerTemp> answer = findAnswer(question);
            if (answer.size() != 0) {
                for (AnswerTemp answerTemp : answer) {
                    String question1 = answerTemp.getQuestion();
                    if (question1.matches(".*" + question + ".*")) {
                        list.add(question);
                        break;
                    }
                }
            }
        }
        if (spiderQuestion.isFlag() && list.size() != 0) {
            spiderQuestion.setQuestions(list);
            rabbitTemplate.convertAndSend("user_notice", spiderQuestion);
        }
    }

    private static class myThread implements Callable<Set<AnswerTemp>> {
        private SimpleSpiderService simpleSpiderService;
        private String question;

        myThread(SimpleSpiderService simpleSpiderService, String question) {
            this.simpleSpiderService = simpleSpiderService;
            this.question = question;
        }

        @Override
        public Set<AnswerTemp> call() {
            return simpleSpiderService.findAnswer(question);
        }
    }
}
