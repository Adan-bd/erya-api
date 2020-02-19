package Spider;

import common.vo.Questions;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import spider.service.SpiderService;

import java.util.ArrayList;

@SpringBootTest
@RunWith(SpringRunner.class)
public class SpiderServiceTest {
        @Autowired
    private SpiderService spiderService;
    @Test
    public void Test(){
//        ArrayList<String> list = new ArrayList<>();
//        list.add("0,0)，B(-1,1)，C(-1,3)，D(2,-3)，则与点P(1,2)位于直线x=y-1=0的同一侧");
//        Questions questions=new Questions();
//        questions.setFlag(true);
//        questions.setOpenid("o_pmG5PuiufQcNLSfjXVqoTzn2n4");
//        questions.setQuestions(list);
//        spiderService.findAnswer(questions);
    }
}
