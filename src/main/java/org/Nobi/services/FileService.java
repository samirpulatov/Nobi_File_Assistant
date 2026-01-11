package org.Nobi.services;


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
import java.util.Collections;
import java.util.List;


@Service
public class FileService {

    private final static Logger LOGGER = LoggerFactory.getLogger(FileService.class);

    @Value("${telegram.bot.token}")
    private String botToken; // getToken for building url

    // Services for working with different files
    private final JpgService jpgService;
    private final PdfService pdfService;

    public FileService(JpgService jpgService, PdfService pdfService) {
        this.jpgService = jpgService;
        this.pdfService = pdfService;
    }

    public List<SendDocument> convertFileTo(Long chat_id, File file, String file_name, String action) {

        //download file and save it locally
        var localFile = saveFileLocally(file,file_name);

        if(localFile!=null)
        {
            //then convert it due to action that user has chosen
            var convertedFile = handleAction(localFile,action);
            return Collections.singletonList(convert_File_To_SendDocument(chat_id, convertedFile));
        }

        return null;
    }

    private java.io.File saveFileLocally(File fileToSave, String file_name) {
        String fileUrl = "https://api.telegram.org/file/bot"+botToken+"/"+fileToSave.getFilePath(); // url for downloading a file

        java.io.File localFile = new java.io.File("downloads/"+file_name);

        //use try-with-resources to always close a file automatically
        try(InputStream inputStream = new URI(fileUrl).toURL().openStream()){
            Files.copy(inputStream,localFile.toPath(), StandardCopyOption.REPLACE_EXISTING);
        } catch (Exception e) {
            LOGGER.error("Error while trying to download file that user has sent", e);
            return null;
        }

        return localFile;

    }

    private java.io.File handleAction(java.io.File localFile, String action) {
        return switch (action) {
            case "JPG_TO_PNG" -> jpgService.convertJPG_TO_PNG(localFile);
            case "JPG_TO_PDF" -> jpgService.convertJPG_TO_PDF(localFile);
            case "JPG_TO_WEBP" -> jpgService.convertJPG_TO_WEBP(localFile);
            case "PDF_TO_WORD" -> pdfService.convertPdf_To_WORD(localFile);
            default -> {
                LOGGER.error("Unknown operation");
                yield null;
            }
        };
    }

    private SendDocument convert_File_To_SendDocument(Long chat_id,java.io.File convertedFile) {

        return SendDocument.builder()
                .chatId(chat_id)
                .document(new InputFile(convertedFile))
                .caption("Отправляю Вам отредактированный файл\uD83D\uDE01!")
                .build();
    }


}
