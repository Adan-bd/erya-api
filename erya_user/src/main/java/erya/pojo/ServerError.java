package erya.pojo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

@Data
@TableName("servererror")
public class ServerError {
    @TableId(type = IdType.AUTO)
    private int id;
    private String question;
    private String time;
    private String dataerr;
}
