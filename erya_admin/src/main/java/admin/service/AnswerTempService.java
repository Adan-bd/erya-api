package admin.service;

import admin.pojo.AnswerTemp;
import com.baomidou.mybatisplus.core.metadata.IPage;

import java.util.List;

public interface AnswerTempService {
    int deleteAnswerTemp(List<Integer> id);

    int modifyAnswerTemp(AnswerTemp answerTemp);

    IPage<AnswerTemp> selectAnswerTemp(int page, int pageSize, String search);

    int mergeAll();

    int merge(List<Integer> answerTemps);

    int removal();
}
