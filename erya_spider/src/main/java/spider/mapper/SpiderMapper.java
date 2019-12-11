package spider.mapper;


import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Delete;
import spider.pojo.Spider;

public interface SpiderMapper extends BaseMapper<Spider> {
    @Delete("delete from spider")
    void deleteAll();
}