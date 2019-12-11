package erya.service.impl;

import erya.mapper.ServerErrorMapper;
import erya.pojo.ServerError;
import erya.service.ServerErrorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ServerErrorServiceImp implements ServerErrorService {
    private ServerErrorMapper serverErrorMapper;

    @Autowired
    public ServerErrorServiceImp(ServerErrorMapper serverErrorMapper) {
        this.serverErrorMapper = serverErrorMapper;
    }

    @Override
    public void insertServerError(ServerError serverErro) {
        serverErrorMapper.insert(serverErro);
    }
}
