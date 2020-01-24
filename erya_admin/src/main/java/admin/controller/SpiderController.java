package admin.controller;

import admin.pojo.Spider;
import admin.service.SpiderService;
import com.baomidou.mybatisplus.core.metadata.IPage;
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
    public ResponseEntity<Integer> dataMerge() {
        return ResponseEntity.status(HttpStatus.OK).body(spiderService.mergeAll());
    }

    @PostMapping("spider/merge")
    public ResponseEntity<Integer> merge(@RequestBody List<Integer> list) {
        return ResponseEntity.status(HttpStatus.OK).body(spiderService.merge(list));
    }

    @PostMapping("spider/{page}/{pageSize}")
    public ResponseEntity<IPage<Spider>> answers(@PathVariable("page") int page, @PathVariable("pageSize") int pageSize, @RequestParam("search") String search) {
        return ResponseEntity.status(HttpStatus.OK)
                .body(spiderService.selectSpider(page, pageSize, search));
    }

    @PostMapping("spider/delete")
    public ResponseEntity<Integer> delete(@RequestBody List<String> answers) {
        return ResponseEntity.status(HttpStatus.OK).body(spiderService.deleteSpider(answers));
    }

    @PostMapping("spider/modify")
    public ResponseEntity<Integer> modify(@RequestBody Spider answer) {
        return ResponseEntity.status(HttpStatus.OK).body(spiderService.modifySpider(answer));
    }
}
