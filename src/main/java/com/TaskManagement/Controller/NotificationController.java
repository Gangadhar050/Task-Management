package com.TaskManagement.Controller;

import com.TaskManagement.DTO.EmailLogDTO;
import com.TaskManagement.Entity.EmailLog;
import com.TaskManagement.Service.NotificationService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/notify")
public class NotificationController {
    @Autowired
     private NotificationService notificationService;

    @PostMapping("send")
    public ResponseEntity<String>sendEmail(@RequestBody EmailLogDTO emailLog){
        String response = notificationService.sendEmail(emailLog);
        return ResponseEntity.ok(response);
    }

}
