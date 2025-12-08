package com.TaskManagement.ServiceIMPL;

import com.TaskManagement.Entity.Attachment;
import com.TaskManagement.Repository.AttachmentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

@Service
@RequiredArgsConstructor
public class AttachmentserviceImpl{
    @Autowired
    private CloudinaryStorageServiceImpl storageService;

    @Autowired
    private AttachmentRepository attachmentRepository;

    @Transactional
    public Attachment upload(Long issueId, MultipartFile file, String uploadedBy){
        String url = storageService.store(file, "issue/" + issueId);
        Attachment attach=new Attachment();
        attach.setIssueId(issueId);
        attach.setFileName(file.getOriginalFilename());
        attach.setContentType(file.getContentType());
        attach.setFileSize(file.getSize());
        attach.setStoragePath(url);
        attach.setUploadby(uploadedBy);

        return attachmentRepository.save(attach);
    }

    @Transactional
    public byte[]download(Long id){
        Attachment attachment = attachmentRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Attachment not found :" + id));
return storageService.read(attachment.getStoragePath());
    }

    @Transactional
    public String getDownloadByFileName(Long id){
        return attachmentRepository.findById(id)
                .map(Attachment::getFileName)
                .orElse("File ");
    }

    @Transactional
    public String getContentType(Long id){
        return attachmentRepository.findById(id)
                .map(Attachment::getContentType)
                .orElse("application/octect-stream");
    }
}
