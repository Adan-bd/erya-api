package erya.service.impl;
import erya.mapper.AppErrorMapper;
import erya.pojo.AppError;
import erya.service.AppErrorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AppErrorServiceImp implements AppErrorService {
    private AppErrorMapper appErrorMapper;

    @Autowired
    public AppErrorServiceImp(AppErrorMapper appErrorMapper) {
        this.appErrorMapper = appErrorMapper;
    }

    @Override
    public void insertAppError(AppError appError) {
        appErrorMapper.insert(appError);
    }

}
