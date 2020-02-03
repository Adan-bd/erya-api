package admin.controller;

import admin.pojo.User;
import admin.service.UserService;
import common.vo.Result;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class UserController {
    private UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("user/modify/{num}")
    public ResponseEntity<Result> modifyNum(@RequestBody List<String> list, @PathVariable("num") int num) {
        return ResponseEntity.status(HttpStatus.OK).body(new Result(userService.modifyNum(list, num)));
    }

    @PostMapping("user/modify/{old}/{new}")
    public ResponseEntity<Result> modifyAtoB(@PathVariable("old") int old, @PathVariable("new") int news) {
        return ResponseEntity.status(HttpStatus.OK).body(new Result(userService.modifyAtoB(old, news)));
    }

    @PostMapping("user/{page}/{pageSize}")
    public ResponseEntity<Result> getUser(@PathVariable("page") int page, @PathVariable("pageSize") int pageSize) {
        return ResponseEntity.status(HttpStatus.OK)
                .body(new Result(userService.selectUser(page, pageSize)));
    }

    @PostMapping("user/modifyAll/{num}")
    public ResponseEntity<Result> modifyAll(@PathVariable("num") int num) {
        return ResponseEntity.status(HttpStatus.OK).body(new Result(userService.modifyAll(num)));
    }

    @PostMapping("user/modify")
    public ResponseEntity<Result> modifyUser(@RequestBody User user) {
        return ResponseEntity.status(HttpStatus.OK).body(new Result(userService.modifyUser(user)));
    }
}
