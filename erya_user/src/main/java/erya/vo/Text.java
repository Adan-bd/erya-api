package erya.vo;

import lombok.Data;

import java.util.List;

@Data
public class Text {
    private String corpus_no;
    private String err_msg;
    private int err_no;
    private List<String> result;
    private String sn;
}
