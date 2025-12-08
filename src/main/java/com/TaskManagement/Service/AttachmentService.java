package com.TaskManagement.Service;

import com.TaskManagement.Entity.Attachment;
import org.springframework.web.multipart.MultipartFile;

public interface AttachmentService {
    Attachment upload(Long issueId, MultipartFile file, String uploadedBy);

    byte[]download(Long id);

    String getDownloadByFileName(Long id);

    String getContentType(Long id);
}
