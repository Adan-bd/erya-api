package common.vo;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class Result {
    private int code=200;
    private Object data;

    public Result(Object data) {
        this.data = data;
    }
}