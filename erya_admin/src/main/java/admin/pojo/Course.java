package admin.pojo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

@Data
@TableName("course")
public class Course {
    @TableId(type = IdType.AUTO)
    private int id;
    private String name;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String content;
}
