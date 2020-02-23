//package auth;
//
//import org.junit.Test;
//import org.junit.runner.RunWith;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.context.SpringBootTest;
//import org.springframework.data.redis.core.StringRedisTemplate;
//import org.springframework.test.context.junit4.SpringRunner;
//
//@RunWith(SpringRunner.class)
//@SpringBootTest
//public class AuthTest {
////    @Autowired
////    private AuthServiceImp authServiceImp;
////    @Autowired
////    private JwtUtil jwtUtil;
//    @Autowired
//    private StringRedisTemplate stringRedisTemplate;
//    @Test
//    public void test(){
////        authServiceImp.login(new User());
////        System.out.println(jwtUtil.createJWT("1","admin","admin"));
////        User user=new User();
////        user.setUsername("admin");
////        user.setPassword("123456789");
////        System.out.println(authServiceImp.login(user));
////        ZSetOperations<String, String> zSetOperations = stringRedisTemplate.opsForZSet();
////        zSetOperations.add("courseSet","1",1);
//////        zSetOperations.add("courseSet","2",8);
//////        zSetOperations.add("courseSet","3",2);
//////        zSetOperations.add("courseSet","4",-1);
//////        zSetOperations.remove("courseSet","1");
//////        zSetOperations.remove("courseSet","1");
////        Set<String> courseSet = zSetOperations.range("courseSet", 1, 5);
////        System.out.println(courseSet.size());
//    }
//
//}