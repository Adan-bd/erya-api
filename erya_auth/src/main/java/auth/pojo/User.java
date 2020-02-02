package auth.pojo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

@Data
@TableName("erya_user")
public class User {
    @TableId(type = IdType.AUTO)
    private int id;
    @TableField(exist = false)
    private long time;
    private String username;
    private String password;
    private String permission;
}
