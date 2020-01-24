package auth.service.imp;

import auth.mapper.UserMapper;
import auth.pojo.User;
import auth.service.AuthService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import common.util.JwtUtil;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public class AuthServiceImp implements AuthService {
    private BCryptPasswordEncoder bCryptPasswordEncoder;
    private UserMapper userMapper;
    private JwtUtil jwtUtil;

    public AuthServiceImp(BCryptPasswordEncoder bCryptPasswordEncoder, UserMapper userMapper, JwtUtil jwtUtil) {
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
        this.userMapper = userMapper;
        this.jwtUtil = jwtUtil;
    }

    @Override
    public Map<String, String> login(User user) {
        String password = user.getPassword();
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("username", user.getUsername());
        user = userMapper.selectOne(queryWrapper);
        if (user != null && bCryptPasswordEncoder.matches(password, user.getPassword())) {
            return jwtUtil.createJWT(String.valueOf(user.getId()), user.getUsername(), user.getPermission());
        }
        return null;
    }

    @Override
    public int register(User user) {
        String password = user.getPassword();
        user.setPassword(bCryptPasswordEncoder.encode(password));
        return userMapper.insert(user);
    }
}
