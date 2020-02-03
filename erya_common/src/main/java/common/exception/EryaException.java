package common.exception;

import lombok.AllArgsConstructor;
import lombok.Data;

@AllArgsConstructor
@Data
public class EryaException extends RuntimeException {
    private int code;
    private String errMsg;

    public EryaException(EryaEnum eryaEnum) {
        this.code = eryaEnum.getCode();
        this.errMsg = eryaEnum.getErrMsg();
    }

    @Override
    public String getMessage() {
        return this.errMsg;
    }
}
