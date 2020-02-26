package auth.controller;

import auth.pojo.User;
import auth.service.AuthService;
import common.vo.Result;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;
import java.util.concurrent.TimeUnit;

@RestController
public class AuthController {
    private AuthService authService;
    private StringRedisTemplate stringRedisTemplate;

    public AuthController(AuthService authService, StringRedisTemplate stringRedisTemplate) {
        this.authService = authService;
        this.stringRedisTemplate = stringRedisTemplate;
    }


    @PostMapping("auth/login")
    public ResponseEntity<Result> login(@RequestBody User user) {
        Map<String, String> map = authService.login(user);
        Result result = new Result();
        if (map == null) {
            return ResponseEntity.status(HttpStatus.OK).body(result);
        }
        stringRedisTemplate.opsForValue().set(map.get("token"), map.get("roles"), Long.parseLong(map.get("expiretime")) - System.currentTimeMillis(), TimeUnit.MILLISECONDS);
        result.setData(map);
        return ResponseEntity.status(HttpStatus.OK).body(result);
    }

    @PostMapping("auth/register")
    public ResponseEntity<Result> register(@RequestBody User user) {
        Result result = new Result(authService.register(user));
        return ResponseEntity.status(HttpStatus.OK).body(result);
    }
}
