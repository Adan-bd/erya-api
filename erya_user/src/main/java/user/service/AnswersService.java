package user.service;

import user.vo.Answers;

import java.util.List;

public interface AnswersService {
    List<Answers> selectAnswers(List<String> questions);
}
