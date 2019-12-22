package user.controller;

import user.pojo.ServerError;
import user.service.ServerErrorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ServerErrorController {
    private ServerErrorService serverErrorService;

    @Autowired
    public ServerErrorController(ServerErrorService serverErrorService) {
        this.serverErrorService = serverErrorService;
    }

    @PostMapping("serverReporter")
    public ResponseEntity<Void> serverReporter(@RequestBody ServerError serverError) {
        serverErrorService.insertServerError(serverError);
        return null;
    }
}
