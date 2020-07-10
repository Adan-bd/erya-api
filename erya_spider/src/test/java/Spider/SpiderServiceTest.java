package Spider;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import common.vo.Result;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import spider.mapper.TempCourseMapper;
import spider.mapper.TempUrlMapper;
import spider.pojo.TempUrl;
import spider.processor.YosoniaDetailProcessor;
import spider.processor.YosoniaPageListProcessor;
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
//        xueXiaoYiImp.findAnswer("测试一下新接口");
    }

    @Autowired
    private Chaoxing360Imp chaoxing360Imp;

    @Test
    public void testChaoXing360() {
//        chaoxing360Imp.findAnswer("测试一下新接口");
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
        xiaofanImp.findAnswer("测试一下新接口");
    }

    @Autowired
    private YosoniaImp yosoniaImp;

    @Test
    public void testYosonia() {
//        yosoniaImp.findAnswer("测试一下新接口");
    }

//    @Autowired
//    private Xuanxiu365Imp xuanxiu365Imp;
//
//    @Test
//    public void testXuanxiu365() {
//        xuanxiu365Imp.findAnswer("测试一下新接口");
//    }

    @Autowired
    private YosoniaPageListProcessor yosoniaPageListProcessor;

    @Autowired
    private TempUrlMapper tempUrlMapper;

    @Autowired
    private TempCourseMapper tempCourseMapper;

    @Test
    public void testYosonnia() throws InterruptedException {
//        for (int i = 1; i <= 4416; i++) {
//            yosoniaPageListProcessor.start("https://www.yosonia.cn/uncategorized/page/" + i, 1, 10000);
//            Thread.sleep(5000);
//        }
    }

    @Autowired
    private YosoniaDetailProcessor yosoniaDetailProcessor;

    @Test
    public void testYosoniaDetail() {
        QueryWrapper<TempUrl> queryWrapper = new QueryWrapper<>();
        queryWrapper.ge("id", 3876);
//        List<TempUrl> tempUrls = tempUrlMapper.selectList(queryWrapper);
//        tempUrls.forEach((tempUrl -> {
//            yosoniaDetailProcessor.start(tempUrl.getUrl(), 1, 10000);
//            try {
//                Thread.sleep(5000);
//            } catch (InterruptedException e) {
//                e.printStackTrace();
//            }
//        }));

//        for (TempUrl tempUrl : tempUrls) {
//            yosoniaDetailProcessor.start(tempUrl.getUrl(), 1, 1000);
//        }
    }

    @Test
    public void testDeleteRepeat() {
//        List<TempUrl> tempUrls = tempUrlMapper.selectList(null);
//        Set<TempUrl> tempUrlSet = new HashSet<>();
//        tempUrls.forEach(tempUrl -> {
//            if (tempUrlSet.contains(tempUrl)) {
//                tempUrlMapper.deleteById(tempUrl.getId());
//            } else {
//                tempUrlSet.add(tempUrl);
//            }
//        });
//        List<TempCourse> tempCourses = tempCourseMapper.selectList(null);
//        Set<TempCourse> tempCourseSet = new HashSet<>();
//        tempCourses.forEach(tempCourse -> {
//            if (tempCourseSet.contains(tempCourse)) {
//                tempUrlMapper.deleteById(tempCourse.getId());
//            } else {
//                tempCourseSet.add(tempCourse);
//            }
//        });
//
    }
}
