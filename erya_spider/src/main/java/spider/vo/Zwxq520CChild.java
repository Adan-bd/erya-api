package spider.vo;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Zwxq520CChild {
    private String answer;
    private long id;
    private long questId;

    @Override
    public String toString() {
        return answer;
    }
}
