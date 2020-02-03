package user.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import user.pojo.AppError;
import user.service.AppErrorService;

@RestController
public class AppErrorController {
    private AppErrorService appErrorService;

    public AppErrorController(AppErrorService appErrorService) {
        this.appErrorService = appErrorService;
    }

    @PostMapping("appErr/appReporter")
    public ResponseEntity<Void> appReporter(@RequestBody AppError appError) {
        appErrorService.insertAppError(appError);
        return ResponseEntity.status(HttpStatus.OK).body(null);
    }
}
