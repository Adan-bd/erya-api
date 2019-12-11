package admin.service.impl;



import admin.mapper.UserMapper;
import admin.pojo.User;
import admin.service.UserService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserServiceImp implements UserService {
    private UserMapper userMapper;

    @Autowired
    public UserServiceImp(UserMapper userMapper) {
        this.userMapper = userMapper;
    }


    @Override
    public IPage<User> selectUser(int pageNo, int pageSize) {
        IPage<User> page = new Page<>(pageNo, pageSize);
        return userMapper.selectPage(page, new QueryWrapper<User>().orderByDesc("num"));
    }

    @Override
    public int modifyAll(int num) {
        User user = new User();
        user.setNum(num);
        return userMapper.update(user, null);
    }

    @Override
    public int modifyUser(User user) {
        return userMapper.updateById(user);
    }

    @Override
    public int modifyNum(List<String> list, int num) {
        User user = new User();
        user.setNum(num);
        int res = 0;
        for (String s : list) {
            user.setOpenid(s);
            res += userMapper.updateById(user);
        }
        return res;
    }

    @Override
    public int modifyAtoB(int old, int news) {
        User user = new User();
        user.setNum(news);
        UpdateWrapper<User> wrapper = new UpdateWrapper<>();
        wrapper.eq("num", old);
        return userMapper.update(user, wrapper);
    }
}
