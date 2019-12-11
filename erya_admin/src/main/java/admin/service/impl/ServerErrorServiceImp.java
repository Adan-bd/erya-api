package admin.service.impl;


import admin.mapper.ServerErrorMapper;
import admin.pojo.ServerError;
import admin.service.ServerErrorService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ServerErrorServiceImp implements ServerErrorService {
    private ServerErrorMapper serverErrorMapper;

    @Autowired
    public ServerErrorServiceImp(ServerErrorMapper serverErrorMapper) {
        this.serverErrorMapper = serverErrorMapper;
    }


    @Override
    public IPage<ServerError> selectServerError(int pageNo, int pageSize, String search) {
        IPage<ServerError> page = new Page<>(pageNo, pageSize);
        QueryWrapper<ServerError> wrapper = new QueryWrapper<>();
        wrapper.like("dataerr", "%" + search + "%");
        return serverErrorMapper.selectPage(page, wrapper);
    }

    @Override
    public int deleteServerError(List<Integer> serviceErrorId) {
        int res = 0;
        for (Integer id : serviceErrorId) {
            res += serverErrorMapper.deleteById(id);
        }
        return res;
    }

    @Override
    public int clear() {
        return serverErrorMapper.delete(null);
    }
}
