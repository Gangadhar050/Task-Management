package com.TaskManagement.ServiceIMPL;

import com.TaskManagement.Entity.Attachment;
import com.TaskManagement.Repository.AttachmentRepository;
import com.TaskManagement.Service.AttachmentService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

@Service
@RequiredArgsConstructor
public class  AttachmentserviceImpl implements AttachmentService {
    @Autowired
    private CloudinaryStorageServiceImpl storageService;

    @Autowired
    private AttachmentRepository attachmentRepository;

    @Transactional
    @Override
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
    @Override
    public byte[]download(Long id){
        Attachment attachment = attachmentRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Attachment not found :" + id));
return storageService.read(attachment.getStoragePath());
    }

    @Transactional
    @Override
    public String getDownloadByFileName(Long id){
        return attachmentRepository.findById(id)
                .map(Attachment::getFileName)
                .orElse("File ");
    }

    @Transactional
    @Override
    public String getContentType(Long id){
        return attachmentRepository.findById(id)
                .map(Attachment::getContentType)
                .orElse("application/octect-stream");
    }
}
