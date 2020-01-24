package user.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import common.vo.Questions;
import user.pojo.Answer;
import user.pojo.AnswerTemp;
import user.vo.Answers;

import java.util.List;

public interface AnswersService {
    List<Answers> selectAnswers(Questions questions);

    IPage<Answer> selectAnswers(int page, int pageSize, String search);

    IPage<AnswerTemp> selectAnswerTemp(int page, int pageSize, String search);
}
