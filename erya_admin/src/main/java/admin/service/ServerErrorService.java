package admin.service;


import admin.pojo.ServerError;
import com.baomidou.mybatisplus.core.metadata.IPage;

import java.util.List;

public interface ServerErrorService {

    IPage<ServerError> selectServerError(int page, int pageSize, String search);

    int deleteServerError(List<Integer> serviceErrorId);

    int clear();
}
