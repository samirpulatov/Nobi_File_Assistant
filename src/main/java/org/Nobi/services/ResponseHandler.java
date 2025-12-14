package org.Nobi.services;

import org.jetbrains.annotations.NotNull;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.generics.TelegramClient;

import java.util.ArrayList;
import java.util.List;

@Service
public class ResponseHandler {

    private final List<CommandHandler> commandHandlers = new ArrayList<>();
    private final MessageSender messageSender;

    public ResponseHandler(MessageSender messageSender){
        this.messageSender = messageSender;
    }

    public void setTelegramClient(TelegramClient telegramClient) {
        messageSender.setTelegramClient(telegramClient);
    }


    public void handleResponse(Update update) {
        String message = update.getMessage().getText();
        long chatId = update.getMessage().getChatId();
        commandHandlers.add(new StartCommand());
        commandHandlers.stream()
                .filter(h -> h.canHandle(message))
                .findFirst()
                .map(h -> h.handle(update))
                .ifPresent(response -> messageSender.sendMessage(chatId,response));

    }
}
