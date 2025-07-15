package com.atm.buenas_practicas_java.services.files;

import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.UUID;

@Service
public class UploadFilesServiceImpl implements IUploadFilesService {
    @Override
    public String handleFileUpload(MultipartFile file) {
        try{
            String fileName = UUID.randomUUID().toString() + "." + file.getOriginalFilename();
            byte[] bytes = file.getBytes();
            String fileOriginalName = file.getOriginalFilename();

            long size = file.getSize();
            long maxFileSize = 5 * 1024 * 1024;

            if(size > maxFileSize){
                return  "Upload File Too Large, must be less or equal to " + maxFileSize;
            }

            assert fileOriginalName != null;
            if(!fileOriginalName.endsWith(".jpg") && !fileOriginalName.endsWith(".jpeg") &&  !fileOriginalName.endsWith(".png"))
            {
                return "Only jpg, jpeg or png files are supported";
            }

            String originalFilename = file.getOriginalFilename();
            assert originalFilename != null;
            String extension = originalFilename.substring(originalFilename.lastIndexOf("."));
            String newFileName = UUID.randomUUID().toString() + extension;

            File folder = new File("C:/Users/Usuario/Documents/CursoJava/Proyecto_TresoraHotels/opt/imagenes");
            if(!folder.exists()){
                folder.mkdir();
            }

            Path path = Paths.get(folder.getPath(), newFileName);
            Files.write(path, bytes);

            return newFileName;

        }catch(Exception e){
            e.printStackTrace();
        }
        return null;
    }
}
