package auth.service;

import auth.pojo.User;

import java.util.Map;

public interface AuthService {
    Map<String, String> login(User user);

    int register(User user);
}
