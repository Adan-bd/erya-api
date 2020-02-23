package user.controller;

import common.vo.Result;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import user.pojo.User;
import user.service.UserService;

@RestController
public class UserController {
    private UserService userService;
    private StringRedisTemplate stringRedisTemplate;

    public UserController(UserService userService, StringRedisTemplate stringRedisTemplate) {
        this.userService = userService;
        this.stringRedisTemplate = stringRedisTemplate;
    }


    @PostMapping("user/login/{code}")
    public ResponseEntity<Result> login(@PathVariable("code") String code) {
        User user = userService.Login(code);
        stringRedisTemplate.opsForValue().set(user.getOpenid(), String.valueOf(user.getNum()));
        Result result = new Result(user);
        return ResponseEntity.status(HttpStatus.OK).body(result);
    }

    @PostMapping("user/change")
    public ResponseEntity<Result> addNum(@RequestBody User user) {
        Result result = new Result(userService.change(user));
        stringRedisTemplate.opsForValue().set(user.getOpenid(), String.valueOf(user.getNum()));
        return ResponseEntity.status(HttpStatus.OK).body(result);
    }

    @PostMapping("user/refresh/{openid}")
    public ResponseEntity<Result> refresh(@PathVariable("openid") String openid) {
        User user;
        String num = stringRedisTemplate.opsForValue().get(openid);
        if (num != null) {
            user = new User(openid, Integer.valueOf(num));
        } else {
            user = userService.getNum(openid);
            stringRedisTemplate.opsForValue().set(user.getOpenid(), String.valueOf(user.getNum()));
        }
        Result result = new Result(user);
        return ResponseEntity.status(HttpStatus.OK).body(result);
    }
}
