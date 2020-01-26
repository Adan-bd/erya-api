package admin.service.impl;


import admin.service.NoticeService;
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

    @Override
    public void setNotice(String notice) {
        redisTemplate.opsForValue().set("notice", notice);
    }

    @Override
    public String getDeploy() {
        return redisTemplate.opsForValue().get("deploy");
    }

    @Override
    public void setDeploy(String deploy) {
        redisTemplate.opsForValue().set("deploy", deploy);
    }
}
