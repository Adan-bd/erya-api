package user.service.impl;

import user.mapper.ProblemMapper;
import user.pojo.Problem;
import user.service.ProblemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ProblemServiceImp implements ProblemService {
    private ProblemMapper problemMapper;

    @Autowired
    public ProblemServiceImp(ProblemMapper problemMapper) {
        this.problemMapper = problemMapper;
    }

    @Override
    public void insertProblem(Problem problem) {
        problemMapper.insert(problem);
    }

}
