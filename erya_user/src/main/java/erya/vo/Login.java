package erya.vo;

import lombok.Data;

@Data
public class Login {
    private String openid;
    private String session_key;
    private String unionid;
    private String errcode;
    private String errmsg;
}
