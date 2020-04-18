package spider.vo;

import lombok.Data;

import java.util.List;

@Data
public class XueXiaoYi {
    private int code;
    private String msg;
    private List<XuexiaoyiQuestion> data;
    private String info;
    private String ask;

    @Data
    public static class XuexiaoyiQuestion{
        private String q;
        private String a;
    }
}
