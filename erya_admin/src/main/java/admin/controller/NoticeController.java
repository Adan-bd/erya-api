package admin.controller;

import admin.service.NoticeService;
import common.vo.Result;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class NoticeController {
    private NoticeService noticeService;

    public NoticeController(NoticeService noticeService) {
        this.noticeService = noticeService;
    }

    @PostMapping(path = "notece/getNotice", produces = "text/json;charset=utf-8")
    public ResponseEntity<Result> getNotice() {
        return ResponseEntity.status(HttpStatus.OK).body(new Result(noticeService.getNotice()));
    }

    @PostMapping("notece/setNotice")
    public ResponseEntity<Void> setNotice(@RequestParam("notice") String notice) {
        noticeService.setNotice(notice);
        return ResponseEntity.status(HttpStatus.OK).body(null);
    }

    @PostMapping("notece/getDeploy")
    public ResponseEntity<Result> getDeploy() {
        return ResponseEntity.status(HttpStatus.OK).body(new Result(noticeService.getDeploy()));
    }

    @PostMapping("notece/setDeploy")
    public ResponseEntity<Void> setDeploy(@RequestParam("deploy") String deploy) {
        noticeService.setDeploy(deploy);
        return ResponseEntity.status(HttpStatus.OK).body(null);
    }
}