package user.controller;

import common.vo.Questions;
import common.vo.Result;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import user.service.AnswersService;

@RestController
public class AnswerController {
    private AnswersService answersService;

    public AnswerController(AnswersService answersService) {
        this.answersService = answersService;
    }

    @PostMapping("getAnswers")
    public ResponseEntity<Result> getAnswers(@RequestBody Questions questions) {
        Result result = new Result(answersService.selectAnswers(questions));
        return ResponseEntity.status(HttpStatus.OK)
                .body(result);
    }

    @PostMapping("answer/{page}/{pageSize}")
    public ResponseEntity<Result> answer(@PathVariable("page") int page, @PathVariable("pageSize") int pageSize, @RequestParam("search") String search) {
        Result result = new Result(answersService.selectAnswers(page, pageSize, search));
        return ResponseEntity.status(HttpStatus.OK)
                .body(result);
    }

    @PostMapping("answerTemp/{page}/{pageSize}")
    public ResponseEntity<Result> answerTemp(@PathVariable("page") int page, @PathVariable("pageSize") int pageSize, @RequestParam("search") String search) {
        Result result = new Result(answersService.selectAnswerTemp(page, pageSize, search));
        return ResponseEntity.status(HttpStatus.OK)
                .body(result);
    }

    @PostMapping("query/{openid}/{time}")
    public ResponseEntity<Result> queryQuestion(@PathVariable("openid") String openid, @PathVariable("time") Long time) {
        Result result = new Result(answersService.selectQuestions(openid,time));
        return ResponseEntity.status(HttpStatus.OK)
                .body(result);
    }
}