package admin.service.impl;

import admin.mapper.AnswerMapper;
import admin.mapper.AnswerTempMapper;
import admin.pojo.AnswerTemp;
import admin.service.AnswerTempService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
public class AnswerTempServiceImp implements AnswerTempService {
    private AnswerTempMapper answerTempMapper;
    private AnswerMapper answerMapper;

    public AnswerTempServiceImp(AnswerTempMapper answerTempMapper, AnswerMapper answerMapper) {
        this.answerTempMapper = answerTempMapper;
        this.answerMapper = answerMapper;
    }

    @Override
    public int deleteAnswerTemp(List<Integer> id) {
        int res = 0;
        for (Integer integer : id) {
            res += answerTempMapper.deleteById(integer);
        }
        return res;
    }

    @Override
    public int modifyAnswerTemp(AnswerTemp answerTemp) {
        return answerTempMapper.updateById(answerTemp);
    }

    @Override
    public IPage<AnswerTemp> selectAnswerTemp(int pageNo, int pageSize, String search) {
        IPage<AnswerTemp> page = new Page<>(pageNo, pageSize);
        QueryWrapper<AnswerTemp> wrapper = new QueryWrapper<>();
        wrapper.like("question", "%" + search + "%").orderByDesc("question");
        return answerTempMapper.selectPage(page, wrapper);
    }

    @Override
    @Transactional
    public int mergeAll() {
        int res = 0;
        List<AnswerTemp> list = answerTempMapper.selectList(null);
        for (AnswerTemp temp : list) {
            res += answerMapper.insertBySpider(temp.getQuestion(), temp.getAnswer());
        }
        answerTempMapper.delete(null);
        return res;
    }

    @Override
    @Transactional
    public int merge(List<Integer> answerTemps) {
        int res = 0;
        for (Integer integer : answerTemps) {
            AnswerTemp answerTemp = answerTempMapper.selectById(integer);
            res += answerMapper.insertBySpider(answerTemp.getQuestion(), answerTemp.getAnswer());
            answerTempMapper.deleteById(answerTemp.getId());
        }
        return res;
    }

    @Override
    public int removal() {
        List<AnswerTemp> list = answerTempMapper.selectList(null);
        int res = list.size();
        Set<AnswerTemp> set = new HashSet<>(list.size());
        set.addAll(list);
        answerTempMapper.delete(null);
        for (AnswerTemp answerTemp : set) {
            answerTempMapper.insert(answerTemp);
        }
        return res - set.size();
    }
}
