package auth.service.imp;

import auth.mapper.UserMapper;
import auth.pojo.User;
import auth.service.AuthService;
import auth.utils.AESUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import common.exception.EryaException;
import common.util.JwtUtil;
import org.apache.tomcat.util.codec.binary.Base64;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.DigestUtils;

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
        try {
            String password = user.getPassword();
            byte[] bytes = Base64.decodeBase64(password);
            String s = DigestUtils.md5DigestAsHex(String.valueOf(user.getTime()).getBytes());
            String decrypt = AESUtil.decrypt(new String(bytes), s);
            QueryWrapper<User> queryWrapper = new QueryWrapper<>();
            queryWrapper.eq("username", user.getUsername());
            user = userMapper.selectOne(queryWrapper);
            if (user != null && bCryptPasswordEncoder.matches(decrypt, user.getPassword())) {
                return jwtUtil.createJWT(String.valueOf(user.getId()), user.getUsername(), user.getPermission());
            }
        } catch (Exception e) {
            throw new EryaException(500, e.getMessage());
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
