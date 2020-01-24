package user.controller;
import org.springframework.http.HttpStatus;
import user.pojo.AppError;
import user.service.AppErrorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AppErrorController {
    private AppErrorService appErrorService;

    public AppErrorController(AppErrorService appErrorService) {
        this.appErrorService = appErrorService;
    }

    @PostMapping("appReporter")
    public ResponseEntity<Void> appReporter(@RequestBody AppError appError) {
        appErrorService.insertAppError(appError);
        return ResponseEntity.status(HttpStatus.OK).body(null);
    }
}
