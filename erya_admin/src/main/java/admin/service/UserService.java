package admin.service;


import admin.pojo.User;
import com.baomidou.mybatisplus.core.metadata.IPage;

import java.util.List;

public interface UserService {

    IPage<User> selectUser(int page, int pageSize);

    int modifyAll(int num);

    int modifyUser(User user);

    int modifyNum(List<String> list, int num);

    int modifyAtoB(int old, int news);
}