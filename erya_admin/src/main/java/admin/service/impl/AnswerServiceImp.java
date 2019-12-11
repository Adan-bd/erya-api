package admin.service.impl;

import admin.mapper.AnswerMapper;
import admin.pojo.Answer;
import admin.service.AnswerService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AnswerServiceImp implements AnswerService {
    private AnswerMapper answerMapper;

    public AnswerServiceImp(AnswerMapper answerMapper) {
        this.answerMapper = answerMapper;
    }
    
    @Override
    public IPage<Answer> selectAnswers(int pageNo, int pageSize, String question) {
        IPage<Answer> page = new Page<>(pageNo, pageSize);
        QueryWrapper<Answer> wrapper = new QueryWrapper<>();
        wrapper.like("question", "%" + question + "%");
        return answerMapper.selectPage(page, wrapper);
    }

    @Override
    public int deleteAnswer(List<Integer> list) {
        int res = 0;
        for (Integer integer : list) {
            res += answerMapper.deleteById(integer);
        }
        return res;
    }

    @Override
    public int modifyAnswer(Answer answer) {
        return answerMapper.updateById(answer);
    }

    @Override
    public int addAnswer(Answer answer) {
        return answerMapper.insert(answer);
    }

}
