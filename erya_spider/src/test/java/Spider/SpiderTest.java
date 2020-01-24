package Spider;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import spider.service.imp.SpiderServiceImp;

import javax.sound.midi.Soundbank;
import java.sql.SQLOutput;
import java.util.ArrayList;
import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest
public class SpiderTest {
//    @Test
//    public void regixTest() {
//        System.out.println("你好啊".matches(".*你好.*"));
//    }
    @Autowired
    private RabbitTemplate rabbitTemplate;
    @Test
    public void testMQ(){
        List<String> list=new ArrayList<>();
        list.add("sda");
        list.add("fgsdfgdf");
        list.add("asdas");
//        rabbitTemplate.convertAndSend("user_notice",list);
    }
}
