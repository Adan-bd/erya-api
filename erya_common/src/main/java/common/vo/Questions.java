package common.vo;

import lombok.Data;

import java.util.List;

@Data
public class Questions {
    private List<String> questions;
    private String openid;
    private boolean flag;
    private String origin;
}
