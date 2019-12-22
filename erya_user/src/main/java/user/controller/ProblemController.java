package user.controller;

import user.pojo.Problem;
import user.service.ProblemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ProblemController {
    private ProblemService problemService;

    @Autowired
    public ProblemController(ProblemService problemService) {
        this.problemService = problemService;
    }

    @PostMapping("problem")
    public ResponseEntity<Void> problem(@RequestBody Problem problem) {
        problemService.insertProblem(problem);
        return null;
    }
}
