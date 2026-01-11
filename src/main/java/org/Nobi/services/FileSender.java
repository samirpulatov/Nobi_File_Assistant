package org.Nobi.services;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.methods.GetFile;
import org.telegram.telegrambots.meta.api.methods.send.SendDocument;
import org.telegram.telegrambots.meta.api.objects.File;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import org.telegram.telegrambots.meta.generics.TelegramClient;

@Service
public class FileSender {

    private final static Logger LOGGER = LoggerFactory.getLogger(FileSender.class);

    @Autowired
    private  TelegramClient telegramClient;

    //method to send document
    public void sendDocument(SendDocument sendDocument)  throws  TelegramApiException {
        telegramClient.execute(sendDocument);
    }

    //method to download
    public File downloadFile(GetFile fileToDownload) {
        try {
            return telegramClient.execute(fileToDownload);
        } catch (TelegramApiException e) {
             LOGGER.error("Failed to download File that user has sent");
        }
        return null;
    }
}
