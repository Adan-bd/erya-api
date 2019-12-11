package admin.controller;

import admin.service.NoticeService;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class NoticeController {
    private NoticeService noticeService;
    private RedisTemplate<String, String> redisTemplate;

    public NoticeController(NoticeService noticeService, RedisTemplate<String, String> redisTemplate) {
        this.noticeService = noticeService;
        this.redisTemplate = redisTemplate;
    }

    @PostMapping(path = "getNotice", produces = "text/json;charset=utf-8")
    public ResponseEntity<String> getNotice() {
        return ResponseEntity.status(HttpStatus.OK).body(noticeService.getNotice());
    }

    @PostMapping("setNotice")
    public ResponseEntity<Void> setNotice(@RequestParam("notice") String notice) {
        noticeService.setNotice(notice);
        return null;
    }

    @PostMapping("getDeploy")
    public ResponseEntity<String> getDeploy() {
        return ResponseEntity.status(HttpStatus.OK).body(redisTemplate.opsForValue().get("deploy"));
    }

    @PostMapping("setDeploy")
    public ResponseEntity<String> setDeploy(@RequestParam("deploy") String deploy) {
        return ResponseEntity.status(HttpStatus.OK).body(deploy);
    }
}