package user.vo;

import lombok.Data;

@Data
public class Token {
    private String access_token;
    private String session_key;
    private String scope;
    private String refresh_token;
    private String session_secret;
    private long expires_in;
}
