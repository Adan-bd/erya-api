package user.pojo;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@TableName("user")
@AllArgsConstructor
public class User {
    @TableId
    private String openid;
    private Integer num;
}
