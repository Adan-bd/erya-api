package admin.controller;


import admin.pojo.Problem;
import admin.service.ProblemService;
import com.baomidou.mybatisplus.core.metadata.IPage;
import common.vo.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class ProblemController {
    private ProblemService problemService;

    @Autowired
    public ProblemController(ProblemService problemService) {
        this.problemService = problemService;
    }

    @PostMapping("problem/getProblem/{page}/{pageSize}")
    public ResponseEntity<Result> getProblem(@PathVariable("page") int page, @PathVariable("pageSize") int pageSize, @RequestParam("search") String search) {
        return ResponseEntity.status(HttpStatus.OK)
                .body(new Result(problemService.selectProblem(page, pageSize, search)));
    }

    @PostMapping("problem/deleteProblem")
    public ResponseEntity<Result> deleteProblem(@RequestBody List<Integer> list) {
        return ResponseEntity.status(HttpStatus.OK)
                .body(new Result(problemService.deleteProblem(list)));
    }

    @PostMapping("problem/clear")
    public ResponseEntity<Result> clear() {
        return ResponseEntity.status(HttpStatus.OK)
                .body(new Result(problemService.clearProblem()));
    }
}
