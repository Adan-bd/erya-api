package admin.controller;

import admin.pojo.AnswerTemp;
import admin.service.AnswerTempService;
import common.vo.Result;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class AnswerTempController {
    private AnswerTempService answerTempService;

    public AnswerTempController(AnswerTempService answerTempService) {
        this.answerTempService = answerTempService;
    }

    @PostMapping("answerTemp/{page}/{pageSize}")
    public ResponseEntity<Result> answers(@PathVariable("page") int page, @PathVariable("pageSize") int pageSize, @RequestParam("search") String search) {
        return ResponseEntity.status(HttpStatus.OK)
                .body(new Result(answerTempService.selectAnswerTemp(page, pageSize, search)));
    }

    @PostMapping("answerTemp/delete")
    public ResponseEntity<Result> delete(@RequestBody List<Integer> answers) {
        return ResponseEntity.status(HttpStatus.OK).body(new Result(answerTempService.deleteAnswerTemp(answers)));
    }

    @PostMapping("answerTemp/modify")
    public ResponseEntity<Result> modify(@RequestBody AnswerTemp answer) {
        return ResponseEntity.status(HttpStatus.OK).body(new Result(answerTempService.modifyAnswerTemp(answer)));
    }

    @PostMapping("answerTemp/mergeAll")
    public ResponseEntity<Result> mergeAll() {
        return ResponseEntity.status(HttpStatus.OK).body(new Result(answerTempService.mergeAll()));
    }

    @PostMapping("answerTemp/merge")
    public ResponseEntity<Result> merge(@RequestBody List<Integer> answerTemps) {
        return ResponseEntity.status(HttpStatus.OK).body(new Result(answerTempService.merge(answerTemps)));
    }

    @PostMapping("answerTemp/removal")
    public ResponseEntity<Result> removal() {
        return ResponseEntity.status(HttpStatus.OK).body(new Result(answerTempService.removal()));
    }
}
