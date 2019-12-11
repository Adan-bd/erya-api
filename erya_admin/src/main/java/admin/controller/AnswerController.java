package admin.controller;


import admin.pojo.Answer;
import admin.service.AnswerService;
import com.baomidou.mybatisplus.core.metadata.IPage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class AnswerController {
    private AnswerService answerService;

    @Autowired
    public AnswerController(AnswerService answerService) {
        this.answerService = answerService;
    }


    @PostMapping("answer/{page}/{pageSize}")
    public ResponseEntity<IPage<Answer>> answers(@PathVariable("page") int page, @PathVariable("pageSize") int pageSize, @RequestParam("search") String search) {
        return ResponseEntity.status(HttpStatus.OK)
                .body(answerService.selectAnswers(page, pageSize, search));
    }

    @PostMapping("answer/delete")
    public ResponseEntity<Integer> delete(@RequestBody List<Integer> answers) {
        return ResponseEntity.status(HttpStatus.OK).body(answerService.deleteAnswer(answers));
    }

    @PostMapping("answer/modify")
    public ResponseEntity<Integer> modify(@RequestBody Answer answer) {
        return ResponseEntity.status(HttpStatus.OK).body(answerService.modifyAnswer(answer));
    }

    @PostMapping("answer/add")
    public ResponseEntity<Integer> add(@RequestBody Answer answer) {
        return ResponseEntity.status(HttpStatus.OK).body(answerService.addAnswer(answer));
    }
}