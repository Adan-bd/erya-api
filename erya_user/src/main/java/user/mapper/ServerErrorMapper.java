package user.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import user.pojo.ServerError;
import org.apache.ibatis.annotations.Delete;


public interface ServerErrorMapper extends BaseMapper<ServerError> {
    @Delete("delete from servererror")
    void clear();
}
