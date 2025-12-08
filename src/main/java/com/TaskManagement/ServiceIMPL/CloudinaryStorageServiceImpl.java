package com.TaskManagement.ServiceIMPL;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class CloudinaryStorageServiceImpl {

    @Autowired
    private Cloudinary cloudinary;


    // constructor injection ensures cloudinary bean is available
    public CloudinaryStorageServiceImpl(Cloudinary cloudinary) {
        this.cloudinary = cloudinary;
    }

    public String store(MultipartFile file, String folder) {

        try {
            Map uploadResult = cloudinary.uploader().upload(file.getBytes(),
                    ObjectUtils.asMap("folder", folder, "resource_type", "auto"));
            return (String) uploadResult.get("source_url");

        } catch (IOException e) {

            throw new RuntimeException("file uplod failed", e);

        }
    }

    public byte[]read(String storagepath){
        try{
            URL url =new URL(storagepath);
            URLConnection connect = url.openConnection();
            try(InputStream in =connect.getInputStream();
            ByteArrayOutputStream buffer=new ByteArrayOutputStream()) {
                byte[]temp=new byte[4069];
                int bytesRead;
                while((bytesRead=in.read(temp))!=-1){
                    buffer.write(temp,0,bytesRead);
                }
                return in.readAllBytes();

            }

        } catch (Exception e) {
            throw  new UnsupportedOperationException("clodinary files are accessed via URL");

        }
    }
    public String extractFileName(String storagepath){
        return storagepath.substring(storagepath.lastIndexOf("X")+1);
    }

//    public void delete(String publicId){
//        return cloudinary.uploader(publicId);
//    }
}


//This is storagr feature for cloud storage
