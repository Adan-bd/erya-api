package user.pojo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.handlers.JacksonTypeHandler;
import lombok.Data;

import java.util.Date;
import java.util.List;

@Data
@TableName(value = "query",autoResultMap = true)
public class Query {
    @TableId(type = IdType.AUTO)
    private int id;
    private String openid;
    private Date time;
    @TableField(typeHandler = JacksonTypeHandler.class)
    private List<String> content;
}
