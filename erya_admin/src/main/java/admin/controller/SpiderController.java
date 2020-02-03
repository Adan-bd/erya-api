package admin.controller;

import admin.pojo.Spider;
import admin.service.SpiderService;
import common.vo.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class SpiderController {
    private SpiderService spiderService;

    @Autowired
    public SpiderController(SpiderService spiderService) {
        this.spiderService = spiderService;
    }

    @PostMapping("spider/mergeAll")
    public ResponseEntity<Result> dataMerge() {
        return ResponseEntity.status(HttpStatus.OK).body(new Result(spiderService.mergeAll()));
    }

    @PostMapping("spider/merge")
    public ResponseEntity<Result> merge(@RequestBody List<Integer> list) {
        return ResponseEntity.status(HttpStatus.OK).body(new Result(spiderService.merge(list)));
    }

    @PostMapping("spider/{page}/{pageSize}")
    public ResponseEntity<Result> answers(@PathVariable("page") int page, @PathVariable("pageSize") int pageSize, @RequestParam("search") String search) {
        return ResponseEntity.status(HttpStatus.OK)
                .body(new Result(spiderService.selectSpider(page, pageSize, search)));
    }

    @PostMapping("spider/delete")
    public ResponseEntity<Result> delete(@RequestBody List<String> answers) {
        return ResponseEntity.status(HttpStatus.OK).body(new Result(spiderService.deleteSpider(answers)));
    }

    @PostMapping("spider/modify")
    public ResponseEntity<Result> modify(@RequestBody Spider answer) {
        return ResponseEntity.status(HttpStatus.OK).body(new Result(spiderService.modifySpider(answer)));
    }
}
