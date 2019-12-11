package admin.service.impl;


import admin.mapper.AppErrorMapper;
import admin.pojo.AppError;
import admin.service.AppErrorService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AppErrorServiceImp implements AppErrorService {
    private AppErrorMapper appErrorMapper;

    @Autowired
    public AppErrorServiceImp(AppErrorMapper appErrorMapper) {
        this.appErrorMapper = appErrorMapper;
    }


    @Override
    public IPage<AppError> selectAppError(int pageNo, int pageSize, String search) {
        IPage<AppError> page = new Page<>(pageNo, pageSize);
        QueryWrapper<AppError> wrapper = new QueryWrapper<>();
        wrapper.like("error","%"+search+"%");
        return appErrorMapper.selectPage(page, wrapper);
    }

    @Override
    public int deleteAppError(List<Integer> AppErrorId) {
        int res=0;
        for (Integer id : AppErrorId) {
            res+=appErrorMapper.deleteById(id);
        }
        return res;
    }

    @Override
    public int clearAppError() {
        return appErrorMapper.delete(null);
    }

}
