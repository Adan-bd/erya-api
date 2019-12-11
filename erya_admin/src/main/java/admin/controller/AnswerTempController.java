package admin.controller;

import admin.pojo.AnswerTemp;
import admin.service.AnswerTempService;
import com.baomidou.mybatisplus.core.metadata.IPage;
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
    public ResponseEntity<IPage<AnswerTemp>> answers(@PathVariable("page") int page, @PathVariable("pageSize") int pageSize, @RequestParam("search") String search) {
        return ResponseEntity.status(HttpStatus.OK)
                .body(answerTempService.selectAnswerTemp(page, pageSize, search));
    }

    @PostMapping("answerTemp/delete")
    public ResponseEntity<Integer> delete(@RequestBody List<Integer> answers) {
        return ResponseEntity.status(HttpStatus.OK).body(answerTempService.deleteAnswerTemp(answers));
    }

    @PostMapping("answerTemp/modify")
    public ResponseEntity<Integer> modify(@RequestBody AnswerTemp answer) {
        return ResponseEntity.status(HttpStatus.OK).body(answerTempService.modifyAnswerTemp(answer));
    }

    @PostMapping("answerTemp/mergeAll")
    public ResponseEntity<Integer> mergeAll() {
        return ResponseEntity.status(HttpStatus.OK).body(answerTempService.mergeAll());
    }

    @PostMapping("answerTemp/merge")
    public ResponseEntity<Integer> merge(@RequestBody List<Integer> answerTemps) {
        return ResponseEntity.status(HttpStatus.OK).body(answerTempService.merge(answerTemps));
    }

    @PostMapping("answerTemp/removal")
    public ResponseEntity<Integer> removal() {
        return ResponseEntity.status(HttpStatus.OK).body(answerTempService.removal());
    }
}
