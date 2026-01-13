package org.Nobi.services;



import org.Nobi.exceptions.FileConversionException;
import org.Nobi.exceptions.FileDownloadException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.methods.send.SendDocument;
import org.telegram.telegrambots.meta.api.objects.File;
import org.telegram.telegrambots.meta.api.objects.InputFile;


import java.io.InputStream;
import java.net.URI;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;



@Service
public class ImageConverterService {

    private final static Logger LOGGER = LoggerFactory.getLogger(ImageConverterService.class);

    @Value("${telegram.bot.token}")
    private String botToken; // getToken for building url

    // Services for working with different files
    private final JpgService jpgService;
    private final PngService pngService;
    private final HeicService heicService;

    public ImageConverterService(JpgService jpgService, PngService pngService, HeicService heicService) {
        this.jpgService = jpgService;
        this.pngService = pngService;
        this.heicService = heicService;
    }

    public SendDocument convertFileTo(Long chat_id, File file, String file_name, String action) {

        try{
            //download file and save it locally
            var localFile = saveFileLocally(file,file_name);


            //then convert it due to action that user has chosen
            var convertedFile = handleAction(localFile,action);

            //then convert it to SendDocument object
            return convert_File_To_SendDocument(chat_id, convertedFile);
        } catch(Exception e){
            throw new FileConversionException(chat_id,file_name,action);
        }

    }

    private java.io.File saveFileLocally(File fileToSave, String file_name) {
        String fileUrl = "https://api.telegram.org/file/bot"+botToken+"/"+fileToSave.getFilePath(); // url for downloading a file

        java.io.File localFile = new java.io.File("downloads/"+file_name);

        //use try-with-resources to always close a file automatically
        try(InputStream inputStream = new URI(fileUrl).toURL().openStream()){
            Files.copy(inputStream,localFile.toPath(), StandardCopyOption.REPLACE_EXISTING);
        } catch (Exception e) {
            LOGGER.error("Error while trying to download file that user has sent", e);
            throw new FileDownloadException(file_name);
        }

        return localFile;

    }

    private java.io.File handleAction(java.io.File localFile, String action) {
        return switch (action) {
            case "JPG_TO_PNG" -> jpgService.convertJPG_TO_PNG(localFile);
            case "JPG_TO_PDF" -> jpgService.convertJPG_TO_PDF(localFile);
            case "JPG_TO_WEBP" -> jpgService.convertJPG_TO_WEBP(localFile);
            case "PNG_TO_PDF" -> pngService.convertJPG_TO_PDF(localFile);
            case "PNG_TO_JPG" -> pngService.convertPNG_TO_JPG(localFile);
            case "PNG_TO_WEBP" -> pngService.convertPNG_TO_WEBP(localFile);
            case "HEIC_TO_PNG" -> heicService.convertHEIC_TO_PNG(localFile);
            case "HEIC_TO_JPG" -> heicService.convertHEIC_TO_JPG(localFile);
            case "HEIC_TO_PDF" -> heicService.convertHEIC_TO_PDF(localFile);
            default -> {
                LOGGER.error("Unknown operation");
                yield null;
            }
        };
    }

    //Function to convert document to SendDocument object
    private SendDocument convert_File_To_SendDocument(Long chat_id,java.io.File convertedFile) {

        return SendDocument.builder()
                .chatId(chat_id)
                .document(new InputFile(convertedFile))
                .caption("Отправляю Вам отредактированный файл\uD83D\uDE01!")
                .build();
    }


}
