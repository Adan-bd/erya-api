package admin.service;

import admin.pojo.Problem;
import com.baomidou.mybatisplus.core.metadata.IPage;

import java.util.List;

public interface ProblemService {

    IPage<Problem> selectProblem(int page, int pageSize, String search);

    int deleteProblem(List<Integer> problemId);

    int clearProblem();
}
