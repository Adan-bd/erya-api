package user.pojo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@TableName("course")
@AllArgsConstructor
@NoArgsConstructor
public class Course implements Serializable {
    @TableId(type = IdType.AUTO)
    private int id;
    private String name;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String content;
}
