package user.service.impl;

import org.springframework.data.redis.core.StringRedisTemplate;
import user.service.NoticeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

@Service
public class NoticeServiceImp implements NoticeService {

    private StringRedisTemplate stringRedisTemplate;

    @Autowired
    public NoticeServiceImp(StringRedisTemplate stringRedisTemplate) {
        this.stringRedisTemplate = stringRedisTemplate;
    }

    @Override
    public String getNotice() {
        return stringRedisTemplate.opsForValue().get("notice");
    }
}
