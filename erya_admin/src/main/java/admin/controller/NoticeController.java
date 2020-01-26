package admin.controller;

import admin.service.NoticeService;
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

    @PostMapping(path = "getNotice", produces = "text/json;charset=utf-8")
    public ResponseEntity<String> getNotice() {
        return ResponseEntity.status(HttpStatus.OK).body(noticeService.getNotice());
    }

    @PostMapping("setNotice")
    public ResponseEntity<Void> setNotice(@RequestParam("notice") String notice) {
        noticeService.setNotice(notice);
        return ResponseEntity.status(HttpStatus.OK).body(null);
    }

    @PostMapping("getDeploy")
    public ResponseEntity<String> getDeploy() {
        return ResponseEntity.status(HttpStatus.OK).body(noticeService.getDeploy());
    }

    @PostMapping("setDeploy")
    public ResponseEntity<Void> setDeploy(@RequestParam("deploy") String deploy) {
        noticeService.setDeploy(deploy);
        return ResponseEntity.status(HttpStatus.OK).body(null);
    }
}