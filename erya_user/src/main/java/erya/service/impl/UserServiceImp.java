package erya.service.impl;

import erya.mapper.UserMapper;
import erya.pojo.User;
import erya.service.UserService;
import erya.vo.Login;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.client.RestTemplate;

@Service
public class UserServiceImp implements UserService {
    private UserMapper userMapper;
    private RestTemplate restTemplate;

    @Autowired
    public UserServiceImp(UserMapper userMapper, RestTemplate restTemplate) {
        this.userMapper = userMapper;
        this.restTemplate = restTemplate;
    }

    @Override
    public User Login(String code) {
        String openid = getOpenid(code);
        if (StringUtils.isEmpty(openid))
            return null;
        User user1 = userMapper.selectById(openid);
        if (user1 == null) {
            userMapper.insertByOpenid(openid);
            return userMapper.selectById(openid);
        } else return user1;
    }

    private String getOpenid(String code) {
        Login login = restTemplate.getForObject(
                "https://api.weixin.qq.com/sns/jscode2session?" +
                        "appid=wx155ae9b028f9ea16&secret=e621606fd1a0369415514b1858c63f58&js_code="
                        + code + "&grant_type=authorization_code", Login.class);
        if (login != null)
            return login.getOpenid();
        else return null;
    }

    @Override
    public int change(User user) {
        return userMapper.updateById(user);
    }

}
