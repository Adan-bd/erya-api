package user;

import common.vo.Result;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import user.mapper.QueryMapper;
import user.pojo.Query;
import user.service.impl.AnswersServiceImp;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest
public class UserTest {
    @Autowired
    private AnswersServiceImp answersServiceImp;
    @Autowired
    private QueryMapper queryMapper;
    @Test
    public void Test(){
//        System.out.println(answersServiceImp.selectQuestions("o_pmG5PuiufQcNLSfjXVqoTzn2n4", 1579966496698L));
        System.out.println(new Result().toString());
    }
}
