package user.service;

import user.pojo.User;

public interface UserService {
    User Login(String code,String type);

    int change(User user);

    User getNum(String openid);
}
