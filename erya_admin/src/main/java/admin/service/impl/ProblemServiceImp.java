package admin.service.impl;

import admin.mapper.ProblemMapper;
import admin.pojo.Problem;
import admin.service.ProblemService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProblemServiceImp implements ProblemService {
    private ProblemMapper problemMapper;

    @Autowired
    public ProblemServiceImp(ProblemMapper problemMapper) {
        this.problemMapper = problemMapper;
    }


    @Override
    public IPage<Problem> selectProblem(int pageNo, int pageSize, String search) {
        IPage<Problem> page = new Page<>(pageNo, pageSize);
        QueryWrapper<Problem> wrapper = new QueryWrapper<>();
        wrapper.like("problem", "%" + search + "%");
        return problemMapper.selectPage(page, wrapper);
    }

    @Override
    public int deleteProblem(List<Integer> problemId) {
        int res = 0;
        for (Integer id : problemId) {
            res += problemMapper.deleteById(id);
        }
        return res;
    }

    @Override
    public int clearProblem() {
        return problemMapper.delete(null);
    }
}
