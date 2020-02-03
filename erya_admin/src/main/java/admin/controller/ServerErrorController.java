package admin.controller;

import admin.service.ServerErrorService;
import common.vo.Result;
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
    public ResponseEntity<Result> getServerError(@PathVariable("page") int page, @PathVariable("pageSize") int pageSize, @RequestParam("search") String search) {
        return ResponseEntity.status(HttpStatus.OK)
                .body(new Result(serverErrorService.selectServerError(page, pageSize, search)));
    }

    @PostMapping("serverErr/deleteServerError")
    public ResponseEntity<Result> deleteServerError(@RequestBody List<Integer> list) {
        return ResponseEntity.status(HttpStatus.OK)
                .body(new Result(serverErrorService.deleteServerError(list)));
    }

    @PostMapping("serverErr/serverError/clear")
    public ResponseEntity<Result> clear() {
        return ResponseEntity.status(HttpStatus.OK)
                .body(new Result(serverErrorService.clear()));

    }
}
