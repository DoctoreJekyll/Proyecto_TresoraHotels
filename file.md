
String fileName = StringUtils.cleanPath(file.getOriginalFilename());

Para comprobar si existe un archivo

public static boolean checkfile(String uploadDir, String fileName){

    Path fullpath = Paths.get(uploadDir + "/" + fileName);

    if (Files.exists(fullpath)) {

        return true;

    }

    else

        return false;
}

Crea si no existe
//check if user folder exists if not create

Files.createDirectories(Paths.get(uploadDirreal));

//Para guardar archivo creo

public static void saveFile(String uploadDir, String fileName,

                            MultipartFile multipartFile) throws IOException {

    Path uploadPath = Paths.get(uploadDir);



    if (!Files.exists(uploadPath)) {

        Files.createDirectories(uploadPath);

    }

    System.out.println("saveFile: Path creado:"+ uploadDir);

    System.out.println("saveFile: fileName: "+ fileName);



    try (InputStream inputStream = multipartFile.getInputStream()) {

        Path filePath = uploadPath.resolve(fileName);

        Files.copy(inputStream, filePath, StandardCopyOption.REPLACE_EXISTING);

    } catch (IOException ioe) {

        throw new IOException("Could not save image file: " + fileName, ioe);

    }

}



