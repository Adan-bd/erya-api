package user.vo;

import lombok.Data;

@Data
public class Voice {
    private String format;
    private int rate;
    private int channel;
    private String token;
    private String cuid;
    private int len;
    private String speech;
}
