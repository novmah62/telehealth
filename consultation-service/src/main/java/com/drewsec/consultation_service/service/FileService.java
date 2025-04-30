package com.drewsec.consultation_service.service;

import org.springframework.web.multipart.MultipartFile;

public interface FileService {

    String saveFile(MultipartFile sourceFile, String userId);
    String uploadFile(MultipartFile sourceFile, String fileUploadSubPath);
    String getFileExtension(String fileName);

}
