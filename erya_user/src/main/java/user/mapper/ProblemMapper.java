package user.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import user.pojo.Problem;
import org.apache.ibatis.annotations.Delete;


public interface ProblemMapper extends BaseMapper<Problem> {
    @Delete("delete from problem")
    void clear();
}
