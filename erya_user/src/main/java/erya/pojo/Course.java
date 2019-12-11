package erya.pojo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

import java.io.Serializable;

@Data
@TableName("course")
public class Course implements Serializable {
    @TableId(type = IdType.AUTO)
    private int id;
    private String name;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String content;
}
