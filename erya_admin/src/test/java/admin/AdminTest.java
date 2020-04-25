package admin;

import admin.mapper.AnswerMapper;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@SpringBootTest
@RunWith(SpringRunner.class)
public class AdminTest {
    @Autowired
    private AnswerMapper answerMapper;

    @Test
    public void deleteRepeat() {
//        List<Answer> answers = answerMapper.selectList(null);
//        System.out.println(answers.size());
//        Set<Answer> answerSet = new HashSet<>();
//        answers.forEach(answer -> {
//            if (answerSet.contains(answer)) {
//                answerMapper.deleteById(answer.getId());
//            } else {
//                answerSet.add(answer);
//            }
//        });
    }
}
