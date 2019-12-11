package erya.pojo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

@Data
@TableName("problem")
public class Problem {
    @TableId(type = IdType.AUTO)
    private int id;
    private String time;
    private String problem;
}
