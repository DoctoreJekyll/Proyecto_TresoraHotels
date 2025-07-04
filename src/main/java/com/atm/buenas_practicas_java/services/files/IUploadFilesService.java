package com.atm.buenas_practicas_java.services.files;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@Service
public interface IUploadFilesService {
    String handleFileUpload(MultipartFile file) throws Exception;
}
