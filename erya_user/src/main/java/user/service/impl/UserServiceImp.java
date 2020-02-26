package user.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import user.mapper.UserMapper;
import user.pojo.User;
import user.service.UserService;
import user.vo.Login;

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
    public User Login(String code, String type) {
        String openid = null;
        switch (type) {
            case "wx":
                openid = getOpenid(code);
                break;
            case "qq":
                openid = getQQOpenid(code);
                break;
            default:
                break;
        }

        User user = userMapper.selectById(openid);
        if (user == null) {
            userMapper.insertByOpenid(openid);
            user = new User(openid,5);
        }
        return user;
    }

    private String getQQOpenid(String code) {
        Login login = restTemplate.getForObject(
                "https://api.q.qq.com/sns/jscode2session?" +
                        "appid=1109737611&secret=saDEVzkeRe5mi9uf&js_code="
                        + code + "&grant_type=authorization_code", Login.class);
        if (login != null)
            return login.getOpenid();
        return null;
    }

    private String getOpenid(String code) {
        Login login = restTemplate.getForObject(
                "https://api.weixin.qq.com/sns/jscode2session?" +
                        "appid=wx155ae9b028f9ea16&secret=e621606fd1a0369415514b1858c63f58&js_code="
                        + code + "&grant_type=authorization_code", Login.class);
        if (login != null)
            return login.getOpenid();
        return null;
    }

    @Override
    public int change(User user) {
        return userMapper.updateById(user);
    }

    @Override
    public User getNum(String openid) {
        return userMapper.selectById(openid);
    }

}
