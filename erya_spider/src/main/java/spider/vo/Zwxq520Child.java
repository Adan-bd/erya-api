package spider.vo;

import lombok.Data;

import java.util.List;

@Data
public class Zwxq520Child {
    private String answerStr;
    private List<Zwxq520CChild> answers;
    private long questId;
    private String question;
    private String subjectName;
}
