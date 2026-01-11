package org.Nobi.services;

import org.Nobi.documents.FileHandler;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.methods.botapimethods.BotApiMethod;
import org.telegram.telegrambots.meta.api.objects.Document;
import org.telegram.telegrambots.meta.api.objects.Update;

import java.util.List;

@Service
public class FileRouter {
    private final List<FileHandler> fileHandlers;

    public FileRouter(List<FileHandler> fileHandlers) {
        this.fileHandlers = fileHandlers;
    }

    public List<BotApiMethod<?>> askForOption(Update update) {
            String file_name = update.getMessage().getDocument().getFileName();

            return fileHandlers.stream()
                    .filter(d -> d.canHandle(file_name))
                    .findFirst()
                    .map( d -> d.handle(update))
                    .orElse(List.of());
    }

}
