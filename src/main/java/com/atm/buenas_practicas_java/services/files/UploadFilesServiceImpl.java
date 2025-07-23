package com.atm.buenas_practicas_java.services.files;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.UUID;

@Service
@Slf4j
public class UploadFilesServiceImpl implements IUploadFilesService {

    @Override
    public String handleFileUpload(MultipartFile file) {
        try {
            log.debug("🔄 Iniciando proceso de subida de archivo");

            String fileOriginalName = file.getOriginalFilename();
            long size = file.getSize();
            long maxFileSize = 5 * 1024 * 1024;

            log.debug("📄 Nombre original: {}", fileOriginalName);
            log.debug("📦 Tamaño archivo: {} bytes", size);

            if (size > maxFileSize) {
                String msg = "⛔ Archivo demasiado grande (max: " + maxFileSize + ")";
                log.warn(msg);
                return msg;
            }

            if (fileOriginalName == null ||
                    (!fileOriginalName.endsWith(".jpg") && !fileOriginalName.endsWith(".jpeg") && !fileOriginalName.endsWith(".png"))) {
                String msg = "⛔ Tipo de archivo no permitido: " + fileOriginalName;
                log.warn(msg);
                return "Only jpg, jpeg or png files are supported";
            }

            String extension = fileOriginalName.substring(fileOriginalName.lastIndexOf("."));
            String newFileName = UUID.randomUUID().toString() + extension;
            byte[] bytes = file.getBytes();

            String folderPath = "/uploads";
            File folder = new File(folderPath);
            if (!folder.exists()) {
                boolean created = folder.mkdirs();
                if (created) {
                    log.info("📁 Carpeta creada: {}", folderPath);
                } else {
                    log.error("❌ No se pudo crear la carpeta: {}", folderPath);
                }
            }

            Path path = Paths.get(folder.getPath(), newFileName);
            Files.write(path, bytes);

            log.info("✅ Imagen subida correctamente: {}", fileOriginalName);
            log.info("📂 Guardada en ruta: {}", path.toAbsolutePath());

            // Mostrar contenido del directorio para verificación en logs
            File[] files = folder.listFiles();
            if (files != null) {
                log.debug("🗂 Contenido actual de /opt/imagenes:");
                for (File f : files) {
                    log.debug("  - {}", f.getName());
                }
            }

            return newFileName;

        } catch (Exception e) {
            log.error("❌ Error durante la subida del archivo", e);
        }
        return null;
    }
}
