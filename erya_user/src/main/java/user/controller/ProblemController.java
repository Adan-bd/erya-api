package user.controller;

import common.vo.Result;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import user.pojo.Problem;
import user.service.ProblemService;

@RestController
public class ProblemController {
    private ProblemService problemService;

    public ProblemController(ProblemService problemService) {
        this.problemService = problemService;
    }

    @PostMapping("problem/upload")
    public ResponseEntity<Result> problem(@RequestBody Problem problem) {
        problemService.insertProblem(problem);
        return ResponseEntity.status(HttpStatus.OK).body(new Result());
    }
}
