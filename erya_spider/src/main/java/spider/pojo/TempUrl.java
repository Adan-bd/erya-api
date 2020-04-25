package spider.pojo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.util.Objects;

@Data
@TableName("temp_url")
public class TempUrl {
    @TableId(type = IdType.AUTO)
    private int id;
    private String url;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        TempUrl tempUrl = (TempUrl) o;
        return Objects.equals(url, tempUrl.url);
    }

    @Override
    public int hashCode() {
        return Objects.hash(url);
    }
}
