package admin.service;


import admin.pojo.Answer;
import com.baomidou.mybatisplus.core.metadata.IPage;

import java.util.List;

public interface AnswerService {

    IPage<Answer> selectAnswers(int page, int pageSize, String search);

    int deleteAnswer(List<Integer> list);

    int modifyAnswer(Answer answer);

    int addAnswer(Answer answer);
}
