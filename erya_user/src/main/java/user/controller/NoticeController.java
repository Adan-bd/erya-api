package user.controller;

import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import user.service.NoticeService;

import java.util.Objects;

@RestController
public class NoticeController {
    private RedisTemplate<String, String> redisTemplate;
    private NoticeService noticeService;

    public NoticeController(RedisTemplate<String, String> redisTemplate, NoticeService noticeService) {
        this.redisTemplate = redisTemplate;
        this.noticeService = noticeService;
    }

    @PostMapping(path = "notice/getNotice/{intent}", produces = "text/json;charset=utf-8")
    public ResponseEntity<String> getNotice(@PathVariable("intent") String intent) {
        if (intent.equals("notice")) {
            return ResponseEntity.status(HttpStatus.OK).body(noticeService.getNotice());
        } else {
            if (Objects.equals(redisTemplate.opsForValue().get("deploy"), "true"))
                return ResponseEntity.status(HttpStatus.OK).body("{\r\n" +
                        "    \"kouling\": \"打开支付宝首页搜“527812077”领红包(长按复制)\",\r\n" +
                        "    \"qitao\": \"坚定不移的发出想要0.1的声音\"\r\n" +
                        "}");
            else return ResponseEntity.status(HttpStatus.OK).body("{\"kouling\":\"网课答案查询\"}");
        }
    }
}