package spider.pojo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

@Data
@TableName("course_detail")
public class CourseDetail {
    @TableId(type = IdType.AUTO)
    private int id;
    private String name;
    private String question;
    private String answer;
}
