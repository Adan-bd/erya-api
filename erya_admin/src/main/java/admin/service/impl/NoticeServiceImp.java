package admin.service.impl;


import admin.service.NoticeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
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

    @Override
    public void setNotice(String notice) {
        stringRedisTemplate.opsForValue().set("notice", notice);
    }

    @Override
    public String getDeploy() {
        return stringRedisTemplate.opsForValue().get("deploy");
    }

    @Override
    public void setDeploy(String deploy) {
        stringRedisTemplate.opsForValue().set("deploy", deploy);
    }
}
