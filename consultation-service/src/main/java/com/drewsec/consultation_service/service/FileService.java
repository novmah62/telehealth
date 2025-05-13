package com.drewsec.consultation_service.service;

import org.springframework.web.multipart.MultipartFile;

import java.util.UUID;

public interface FileService {

    String saveFile(MultipartFile sourceFile, UUID userId);
    String uploadFile(MultipartFile sourceFile, String fileUploadSubPath);
    String getFileExtension(String fileName);

}
