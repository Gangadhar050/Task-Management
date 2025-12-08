package com.TaskManagement.Service;

import com.TaskManagement.DTO.EmailLogDTO;
import com.TaskManagement.Entity.EmailLog;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service

public interface NotificationService {
    String sendEmail(EmailLogDTO emailLog);
//    public void sendEmail(EmailLog emailLog);
}
