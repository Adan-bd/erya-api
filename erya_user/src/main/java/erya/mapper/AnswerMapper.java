package erya.mapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import erya.pojo.Answer;
import org.apache.ibatis.annotations.Insert;

public interface AnswerMapper extends BaseMapper<Answer> {
    @Insert("insert into answer (question,answer) values (#{question},#{answer}) ON DUPLICATE KEY UPDATE answer =#{answer}")
    int insertBySpider(String question, String answer);
}
