package erya.service.impl;

import erya.mapper.ProblemMapper;
import erya.pojo.Problem;
import erya.service.ProblemService;
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
