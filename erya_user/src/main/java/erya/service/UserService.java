package erya.service;

import erya.pojo.User;

public interface UserService {
    User Login(String code);

    int change(User user);
}
