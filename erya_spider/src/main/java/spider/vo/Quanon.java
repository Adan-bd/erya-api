package spider.vo;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Quanon {
    private String question;
    private String answer;
    @JsonProperty("MSGINFO")
    private String MSGINFO;
    @JsonProperty("MSGCODE")
    private int MSGCODE;
    private int accessCount;

    public String getQuestion() {
        return question;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

    public String getAnswer() {
        return answer;
    }

    public void setAnswer(String answer) {
        this.answer = answer;
    }

    public int getAccessCount() {
        return accessCount;
    }

    public void setAccessCount(int accessCount) {
        this.accessCount = accessCount;
    }

    @Override
    public String toString() {
        return "Quanon{" +
                "question='" + question + '\'' +
                ", answer='" + answer + '\'' +
                ", MSGINFO='" + MSGINFO + '\'' +
                ", MSGCODE=" + MSGCODE +
                ", accessCount=" + accessCount +
                '}';
    }
}
