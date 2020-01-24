package auth;

import auth.pojo.User;
import auth.service.imp.AuthServiceImp;
import com.baomidou.mybatisplus.annotation.TableId;
import common.util.JwtUtil;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class AuthTest {
    @Autowired
    private AuthServiceImp authServiceImp;
    @Autowired
    private JwtUtil jwtUtil;
    @Test
    public void test(){
//        authServiceImp.login(new User());
//        System.out.println(jwtUtil.createJWT("1","admin","admin"));
        User user=new User();
        user.setUsername("admin");
        user.setPassword("123456789");
        System.out.println(authServiceImp.login(user));
    }

}
