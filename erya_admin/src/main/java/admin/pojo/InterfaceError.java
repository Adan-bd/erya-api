package admin.pojo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

@Data
@TableName("interface_error")
public class InterfaceError {
    @TableId(type = IdType.AUTO)
    private int id;
    private String name;
    private String url;
    private String errmsg;
}
