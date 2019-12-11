package erya.controller;

import erya.service.AnswersService;
import erya.vo.Answers;
import erya.vo.Questions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class AnswerController {
    private AnswersService answersService;

    @Autowired
    public AnswerController(AnswersService answersService) {
        this.answersService = answersService;
    }

    @PostMapping("getAnswers")
    public ResponseEntity<List<Answers>> getAnswers(@RequestBody Questions questions) {
        return ResponseEntity.status(HttpStatus.OK)
                .body(answersService.selectAnswers(questions.getQuestion()));
    }

}