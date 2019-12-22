package user.controller;

import user.pojo.User;
import user.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UserController {
    private UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("user/login/{code}")
    public ResponseEntity<User> login(@PathVariable("code") String code) {
        return ResponseEntity.status(HttpStatus.OK).body(userService.Login(code));
    }

    @PostMapping("user/change")
    public ResponseEntity<Integer> addNum(@RequestBody User user) {
        return ResponseEntity.status(HttpStatus.OK).body(userService.change(user));
    }

}
