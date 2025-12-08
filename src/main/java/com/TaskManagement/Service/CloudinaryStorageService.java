package com.TaskManagement.Service;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
public interface CloudinaryStorageService {
    String store(MultipartFile file, String folder);

    byte[]read(String storagepath);

    String extractFileName(String storagepath);
//    String store(MultipartFile file, String folder);
}
