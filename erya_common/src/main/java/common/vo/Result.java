package common.vo;

import common.exception.EryaEnum;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class Result {
    private int code=200;
    private Object data;

    public Result(Object data) {
        if(data instanceof EryaEnum){
            this.code=((EryaEnum) data).getCode();
            this.data=((EryaEnum) data).getErrMsg();
        }else{
            this.data = data;
        }
    }
}