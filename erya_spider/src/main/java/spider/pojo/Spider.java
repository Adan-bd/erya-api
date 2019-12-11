package spider.pojo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

@Data
@TableName("spider")
public class Spider {
    @TableId(type = IdType.AUTO)
    private int id;
    private String question;
    private String answer;
}