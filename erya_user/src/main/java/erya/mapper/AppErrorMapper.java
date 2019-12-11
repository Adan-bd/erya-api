package erya.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import erya.pojo.AppError;
import org.apache.ibatis.annotations.Delete;

public interface AppErrorMapper extends BaseMapper<AppError> {
    @Delete("delete from apperror")
    void clear();
}
