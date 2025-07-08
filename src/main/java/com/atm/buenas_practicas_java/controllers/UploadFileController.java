package com.atm.buenas_practicas_java.controllers;

import com.atm.buenas_practicas_java.services.files.IUploadFilesService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

@Controller
@RequestMapping("/upload")
public class UploadFileController {

    private final IUploadFilesService uploadFilesService;

    public UploadFileController(IUploadFilesService uploadFilesService) {
        this.uploadFilesService = uploadFilesService;
    }

    @PostMapping("/guardar")
    public String uploadFile(@RequestParam("file") MultipartFile file) {
        return null;
    }

}
