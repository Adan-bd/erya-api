package erya.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import erya.pojo.ServerError;
import org.apache.ibatis.annotations.Delete;


public interface ServerErrorMapper extends BaseMapper<ServerError> {
    @Delete("delete from servererror")
    void clear();
}
