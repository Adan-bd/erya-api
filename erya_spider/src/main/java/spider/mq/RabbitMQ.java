package spider.mq;

import common.vo.Questions;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;
import spider.service.SpiderService;

@Component
public class RabbitMQ {
    private SpiderService spiderService;

    public RabbitMQ(SpiderService spiderService) {
        this.spiderService = spiderService;
    }

    @RabbitListener(queues = "spider")
    public void getAnswer(Questions questions) {
        spiderService.findAnswer(questions);
    }
}
