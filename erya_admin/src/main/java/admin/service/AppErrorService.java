package admin.service;

import admin.pojo.AppError;
import com.baomidou.mybatisplus.core.metadata.IPage;

import java.util.List;

public interface AppErrorService {

    IPage<AppError> selectAppError(int page, int pageSize, String search);

    int deleteAppError(List<Integer> AppErrorId);

    int clearAppError();
}
