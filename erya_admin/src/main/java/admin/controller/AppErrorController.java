package admin.controller;

import admin.pojo.AppError;
import admin.service.AppErrorService;
import com.baomidou.mybatisplus.core.metadata.IPage;
import common.vo.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class AppErrorController {
    private AppErrorService appErrorService;

    @Autowired
    public AppErrorController(AppErrorService appErrorService) {
        this.appErrorService = appErrorService;
    }


    @PostMapping("appErr/getAppError/{page}/{pageSize}")
    public ResponseEntity<Result> getAppError(@PathVariable("page") int page, @PathVariable("pageSize") int pageSize, @RequestParam("search") String search) {
        return ResponseEntity.status(HttpStatus.OK)
                .body(new Result(appErrorService.selectAppError(page, pageSize, search)));
    }

    @PostMapping("appErr/deleteAppError")
    public ResponseEntity<Result> deleteAppError(@RequestBody List<Integer> list) {
        return ResponseEntity.status(HttpStatus.OK)
                .body(new Result(appErrorService.deleteAppError(list)));
    }

    @PostMapping("appErr/clear")
    public ResponseEntity<Result> clear() {
        return ResponseEntity.status(HttpStatus.OK)
                .body(new Result(appErrorService.clearAppError()));
    }
}
