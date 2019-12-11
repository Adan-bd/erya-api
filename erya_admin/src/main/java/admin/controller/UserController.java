package admin.controller;

import admin.pojo.User;
import admin.service.UserService;
import com.baomidou.mybatisplus.core.metadata.IPage;
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
    public ResponseEntity<Integer> modifyNum(@RequestBody List<String> list, @PathVariable("num") int num) {
        return ResponseEntity.status(HttpStatus.OK).body(userService.modifyNum(list, num));
    }

    @PostMapping("user/modify/{old}/{new}")
    public ResponseEntity<Integer> modifyAtoB(@PathVariable("old") int old, @PathVariable("new") int news) {
        return ResponseEntity.status(HttpStatus.OK).body(userService.modifyAtoB(old, news));
    }

    @PostMapping("user/{page}/{pageSize}")
    public ResponseEntity<IPage<User>> getUser(@PathVariable("page") int page, @PathVariable("pageSize") int pageSize) {
        return ResponseEntity.status(HttpStatus.OK)
                .body(userService.selectUser(page, pageSize));
    }

    @PostMapping("user/modifyAll/{num}")
    public ResponseEntity<Integer> modifyAll(@PathVariable("num") int num) {
        return ResponseEntity.status(HttpStatus.OK).body(userService.modifyAll(num));
    }

    @PostMapping("user/modify")
    public ResponseEntity<Integer> modifyUser(@RequestBody User user) {
        return ResponseEntity.status(HttpStatus.OK).body(userService.modifyUser(user));
    }
}
