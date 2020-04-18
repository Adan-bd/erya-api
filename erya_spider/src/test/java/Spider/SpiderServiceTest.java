package Spider;

import common.vo.Result;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import spider.service.SpiderService;
import spider.service.imp.*;

@SpringBootTest
@RunWith(SpringRunner.class)
public class SpiderServiceTest {
    @Autowired
    private SpiderService spiderService;

    @Test
    public void Test() {
//        ArrayList<String> list = new ArrayList<>();
//        list.add("0,0)，B(-1,1)，C(-1,3)，D(2,-3)，则与点P(1,2)位于直线x=y-1=0的同一侧");
//        Questions questions=new Questions();
//        questions.setFlag(true);
//        questions.setOpenid("o_pmG5PuiufQcNLSfjXVqoTzn2n4");
//        questions.setQuestions(list);
//        spiderService.findAnswer(questions);
        Result result = new Result();
        System.out.println(result.toString());
    }

    @Autowired
    private XueXiaoYiImp xueXiaoYiImp;

    @Test
    public void testXueXiaoYi() {
        xueXiaoYiImp.findAnswer("测试一下新接口");
    }

    @Autowired
    private Chaoxing360Imp chaoxing360Imp;

    @Test
    public void testChaoXing360() {
        chaoxing360Imp.findAnswer("测试一下新接口");
    }

    @Autowired
    private Fm210Imp fm210Imp;

    @Test
    public void testFm210() {
        fm210Imp.findAnswer("测试一下新接口");
    }

    @Autowired
    private Han8Imp han8Imp;

    @Test
    public void testHan8() {
        han8Imp.findAnswer("测试一下新接口");
    }

    @Autowired
    private XiaofanImp xiaofanImp;

    @Test
    public void testXiaofan() {
        xueXiaoYiImp.findAnswer("测试一下新接口");
    }

    @Autowired
    private YosoniaImp yosoniaImp;

    @Test
    public void testYosonia() {
        yosoniaImp.findAnswer("测试一下新接口");
    }

//    @Autowired
//    private Xuanxiu365Imp xuanxiu365Imp;
//
//    @Test
//    public void testXuanxiu365() {
//        xuanxiu365Imp.findAnswer("测试一下新接口");
//    }
}
