package spider.vo;

import lombok.Data;

import java.util.Date;

@Data
public class Datas {
    private int id;
    private String name;
    private String content;
    private Date create_at;
    private int status;
    private int pid;
}
