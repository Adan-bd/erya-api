package user.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import common.vo.Questions;
import lombok.Data;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.stereotype.Service;
import user.mapper.AnswerMapper;
import user.mapper.AnswerTempMapper;
import user.mapper.QueryMapper;
import user.pojo.Answer;
import user.pojo.AnswerTemp;
import user.pojo.Query;
import user.service.AnswersService;
import user.vo.Answers;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

@Service
public class AnswersServiceImp implements AnswersService {
    private AnswerMapper answerMapper;
    private ThreadPoolTaskExecutor taskExecutor;
    private RabbitTemplate rabbitTemplate;
    private AnswerTempMapper answerTempMapper;
    private QueryMapper queryMapper;

    public AnswersServiceImp(AnswerMapper answerMapper, ThreadPoolTaskExecutor taskExecutor, RabbitTemplate rabbitTemplate, AnswerTempMapper answerTempMapper, QueryMapper queryMapper) {
        this.answerMapper = answerMapper;
        this.taskExecutor = taskExecutor;
        this.rabbitTemplate = rabbitTemplate;
        this.answerTempMapper = answerTempMapper;
        this.queryMapper = queryMapper;
    }


    @Override
    public List<Answers> selectAnswers(Questions ques) {
        List<String> questions = ques.getQuestions();
        int size = questions.size();
        List<Answers> answersList = new ArrayList<>(size);
        List<Future<Result>> futures = new ArrayList<>();
        for (String question : questions) {
            Future<Result> future = taskExecutor.submit(new SelectTask(question, answerMapper));
            futures.add(future);
        }
        List<String> answerList = new ArrayList<>();
        int i = 0;
        for (Future<Result> future : futures) {
            try {
                Result result = future.get();
                Answers answers = new Answers();
                answers.setFlag(result.isFlag());
                List<Answer> list = result.getAnswers();
                if (list != null) {
                    answers.setAnswers(list);
                } else {
                    Answer answer = new Answer();
                    answer.setQuestion(questions.get(i));
                    answer.setAnswer("没有找到答案");
                    answerList.add(questions.get(i));
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
        if (answerList.size() > 0) {
            Questions spiderQuestion = new Questions();
            spiderQuestion.setQuestions(answerList);
            spiderQuestion.setOpenid(ques.getOpenid());
            spiderQuestion.setFlag(ques.isFlag());
            spiderQuestion.setOrigin(ques.getOrigin());
            rabbitTemplate.convertAndSend("spider", spiderQuestion);
        }
        return answersList;
    }

    @Override
    public IPage<Answer> selectAnswers(int pageNo, int pageSize, String question) {
        IPage<Answer> page = new Page<>(pageNo, pageSize);
        QueryWrapper<Answer> wrapper = new QueryWrapper<>();
        wrapper.like("question", "%" + question + "%");
        return answerMapper.selectPage(page, wrapper);
    }

    @Override
    public IPage<AnswerTemp> selectAnswerTemp(int pageNo, int pageSize, String search) {
        IPage<AnswerTemp> page = new Page<>(pageNo, pageSize);
        QueryWrapper<AnswerTemp> wrapper = new QueryWrapper<>();
        wrapper.like("question", "%" + search + "%").orderByDesc("question");
        return answerTempMapper.selectPage(page, wrapper);
    }

    @Override
    public List<String> selectQuestions(String openid, Long time) {
        if (time % 1000 >= 500) {
            time += time;
        }
        QueryWrapper<Query> queryWrapper = new QueryWrapper<>();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        queryWrapper.select("content")
                .eq("openid", openid)
                .eq("time", sdf.format(new Date(time)));
        Query query = queryMapper.selectOne(queryWrapper);
        if (query == null)
            return null;
        return query.getContent();
    }


    private static class SelectTask implements Callable<Result> {
        private String question;
        private AnswerMapper answerMapper;

        SelectTask(String question, AnswerMapper answerMapper) {
            this.question = question;
            this.answerMapper = answerMapper;
        }

        @Override
        public Result call() {
            Result result = new Result();
            QueryWrapper<Answer> wrapper = new QueryWrapper<>();
            wrapper.like("question", "%" + question + "%");
            Integer res = answerMapper.selectCount(wrapper);
            if (res == 0) {
                return result;
            } else if (res > 10) {
                result.setFlag(true);
            }
            wrapper.last("limit 10");
            result.setAnswers(answerMapper.selectList(wrapper));
            return result;
        }
    }

    @Data
    private static class Result {
        private List<Answer> answers;
        private boolean flag;
    }

}
