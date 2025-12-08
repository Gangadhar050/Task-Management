package com.TaskManagement.Controller;

import com.TaskManagement.Entity.Attachment;
import com.TaskManagement.ServiceIMPL.AttachmentserviceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/api/file_upload")
public class  AttachmentController {

    @Autowired
    private AttachmentserviceImpl attachmentservice;

    @PostMapping("/upload")
    public ResponseEntity<Attachment>upload(@RequestParam Long issueId, @RequestParam MultipartFile file,@RequestParam String uploadedBy){
        Attachment upload = attachmentservice.upload(issueId,file,uploadedBy);
        return ResponseEntity.ok(upload);
    }
    @GetMapping("/{id}/download")
    public ResponseEntity<byte[]>download(@PathVariable Long id){
        byte[] data = attachmentservice.download(id);
        String fileName = attachmentservice.getDownloadByFileName(id);
        String contentType = attachmentservice.getContentType(id);
        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION,"attachment; filename=\""  +fileName + "\"")
                .contentType(MediaType.parseMediaType(contentType)).body(data);
    }
}
