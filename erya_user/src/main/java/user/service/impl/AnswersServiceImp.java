package user.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import user.mapper.AnswerMapper;
import user.pojo.Answer;
import user.service.AnswersService;
import user.vo.Answers;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

@Service
public class AnswersServiceImp implements AnswersService {
    private AnswerMapper answerMapper;
    private ThreadPoolTaskExecutor taskExecutor;
    private RestTemplate restTemplate;

    public AnswersServiceImp(AnswerMapper answerMapper, ThreadPoolTaskExecutor taskExecutor, RestTemplate restTemplate) {
        this.answerMapper = answerMapper;
        this.taskExecutor = taskExecutor;
        this.restTemplate = restTemplate;
    }

    @Override
    public List<Answers> selectAnswers(List<String> questions) {
        int size = questions.size();
        List<Answers> answersList = new ArrayList<>(size);
        List<Future<List<Answer>>> futures = new ArrayList<>();
        for (String question : questions) {
            Future<List<Answer>> future = taskExecutor.submit(new SelectTask(question, answerMapper));
            futures.add(future);
        }
        int i = 0;
        for (Future<List<Answer>> future : futures) {
            try {
                Answers answers = new Answers();
                List<Answer> list = future.get();
                if (list.size() != 0) {
                    answers.setAnswers(list);
                } else {
                    Answer answer = new Answer();
                    answer.setQuestion(questions.get(i));
                    answer.setAnswer("没有找到答案");
                    getAnswers(questions.get(i));
                    List<Answer> list1 = new ArrayList<>();
                    list1.add(answer);
                    answers.setAnswers(list1);
                }
                answersList.add(answers);
                i++;
            } catch (InterruptedException | ExecutionException e) {
                e.printStackTrace();
            }
        }
        return answersList;
    }

    private void getAnswers(String question) {
        taskExecutor.submit(() -> {
            MultiValueMap<String, Object> postParameters = new LinkedMultiValueMap<>();
            postParameters.add("question", question);
            HttpHeaders headers = new HttpHeaders();
            headers.add("Content-Type", "application/x-www-form-urlencoded");
            HttpEntity<MultiValueMap<String, Object>> r = new HttpEntity<>(postParameters, headers);
            restTemplate.postForObject("https://spider.erya.ychstudy.cn/getAnswer", r, Void.class);
        });
//        restTemplate.postForObject("http://localhost:8082/getAnswer", r, Void.class);
    }

    private static class SelectTask implements Callable<List<Answer>> {
        private String question;
        private AnswerMapper answerMapper;

        SelectTask(String question, AnswerMapper answerMapper) {
            this.question = question;
            this.answerMapper = answerMapper;
        }

        @Override
        public List<Answer> call() {
            QueryWrapper<Answer> wrapper = new QueryWrapper<>();
            wrapper.like("question", "%" + question + "%");
            return answerMapper.selectList(wrapper);
        }
    }

}
