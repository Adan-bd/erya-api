package admin.controller;

import admin.pojo.ServerError;
import admin.service.ServerErrorService;
import com.baomidou.mybatisplus.core.metadata.IPage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class ServerErrorController {
    private ServerErrorService serverErrorService;

    @Autowired
    public ServerErrorController(ServerErrorService serverErrorService) {
        this.serverErrorService = serverErrorService;
    }

    @PostMapping("serverErr/getServerError/{page}/{pageSize}")
    public ResponseEntity<IPage<ServerError>> getServerError(@PathVariable("page") int page, @PathVariable("pageSize") int pageSize, @RequestParam("search") String search) {
        return ResponseEntity.status(HttpStatus.OK)
                .body(serverErrorService.selectServerError(page, pageSize, search));
    }

    @PostMapping("serverErr/deleteServerError")
    public ResponseEntity<Integer> deleteServerError(@RequestBody List<Integer> list) {
        return ResponseEntity.status(HttpStatus.OK)
                .body(serverErrorService.deleteServerError(list));
    }

    @PostMapping("serverErr/serverError/clear")
    public ResponseEntity<Integer> clear() {
        return ResponseEntity.status(HttpStatus.OK)
                .body(serverErrorService.clear());

    }
}
