package erya.service.impl;

import erya.service.NoticeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

@Service
public class NoticeServiceImp implements NoticeService {

    private RedisTemplate<String, String> redisTemplate;

    @Autowired
    public NoticeServiceImp(RedisTemplate<String, String> redisTemplate) {
        this.redisTemplate = redisTemplate;
    }

    @Override
    public String getNotice() {
        return redisTemplate.opsForValue().get("notice");
    }
}
