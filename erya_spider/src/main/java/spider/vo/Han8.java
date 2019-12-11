package spider.vo;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class Han8 {
    @JsonProperty("Net-Work")
    private String Net_Work;
    private String problem;
    private String answer;
}
