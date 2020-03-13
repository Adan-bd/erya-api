package user.mq;

import common.vo.Questions;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import user.mapper.QueryMapper;
import user.pojo.Query;

import java.util.Date;
import java.util.Objects;

@Component
public class RabbitMQ {
    private RestTemplate restTemplate;
    private QueryMapper queryMapper;

    public RabbitMQ(RestTemplate restTemplate, QueryMapper queryMapper) {
        this.restTemplate = restTemplate;
        this.queryMapper = queryMapper;
    }

    @RabbitListener(queues = "user_notice")
    public void getAnswer(Questions questions) {
        long time = System.currentTimeMillis();
        Query query = new Query();
        query.setOpenid(questions.getOpenid());
        query.setTime(new Date(time));
        query.setContent(questions.getQuestions());
        queryMapper.insert(query);
        if (questions.getOrigin() == null) {
            sendWXNotify(questions.getQuestions().get(0), questions.getOpenid(), time);
        } else {
            sendQQNotify(questions.getQuestions().get(0), questions.getOpenid(), time);
        }
    }

    private void sendQQNotify(String question, String openid, long time) {
        Access_Token access_token = restTemplate.getForObject("https://api.q.qq.com/api/getToken?" +
                "grant_type=client_credential&appid=1109737611&secret=saDEVzkeRe5mi9uf", Access_Token.class);
        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Type", "application/json");
        SendRequest sendRequest = new SendRequest(openid, time);
        sendRequest.setTemplate_id("a367f9f5b5cbcddc6ee7efb56ec4ca6b");
        String data = question.length() > 20 ? question.substring(0, 16) : question;
        sendRequest.setData(new QQTemplate(new ReqData(data + " 等问题"), new ReqData("点击查看")));
        HttpEntity<SendRequest> r = new HttpEntity<>(sendRequest, headers);
        restTemplate.postForObject("https://api.q.qq.com/api/json/subscribe/SendSubscriptionMessage?access_token=" + Objects.requireNonNull(access_token).getAccess_token(), r, Void.class);
    }

    private void sendWXNotify(String question, String openid, long time) {
        Access_Token access_token = restTemplate.getForObject("https://api.weixin.qq.com/cgi-bin/token?" +
                "grant_type=client_credential&appid=wx155ae9b028f9ea16&secret=e621606fd1a0369415514b1858c63f58", Access_Token.class);
        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Type", "application/json");
        SendRequest sendRequest = new SendRequest(openid, time);
        sendRequest.setTemplate_id("XRiWYJ2-pWtjMSA1tVa4Gr1rLN3wKzrEuZY_DOGjBmw");
        String data = question.length() > 20 ? question.substring(0, 16) : question;
        sendRequest.setData(new WxTemplate(new ReqData(data + " 等问题"), new ReqData("点击查看")));
        HttpEntity<SendRequest> r = new HttpEntity<>(sendRequest, headers);
        restTemplate.postForObject("https://api.weixin.qq.com/cgi-bin/message/subscribe/send?access_token=" + Objects.requireNonNull(access_token).getAccess_token(), r, Void.class);
    }

    @Data
    private static class Access_Token {
        private String access_token;
        private int expires_in;
    }

    @Data
    private static class SendRequest {
        private String touser;
        private String template_id;
        private String page = "pages/answer/answer?";
        private Object data;

        public SendRequest(String openid, long time) {
            String param = "openid=" + openid + "&time=" + time;
            page += param;
            this.touser = openid;
        }
    }

    @Data
    @AllArgsConstructor
    private static class WxTemplate {
        private ReqData thing1;
        private ReqData thing2;
    }

    @Data
    @AllArgsConstructor
    private static class QQTemplate {
        private ReqData keyword1;
        private ReqData keyword2;
    }

    @Data
    @AllArgsConstructor
    private static class ReqData {
        private String value;
    }
}
