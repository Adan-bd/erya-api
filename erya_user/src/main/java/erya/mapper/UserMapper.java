package erya.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import erya.pojo.User;
import org.apache.ibatis.annotations.Insert;

public interface UserMapper extends BaseMapper<User> {
    @Insert("insert into user (openid) values(#{openid})")
    int insertByOpenid(String openid);
}
