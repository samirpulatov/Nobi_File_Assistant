package org.Nobi.services;

import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import org.telegram.telegrambots.meta.generics.TelegramClient;


@Service
public class MessageSender {

    private TelegramClient telegramClient;

    public void setTelegramClient(TelegramClient telegramClient) {
        this.telegramClient  = telegramClient;
    }

    public void sendMessage(Long chat_id, String message){
        SendMessage sendMessage = SendMessage
                .builder()
                .chatId(chat_id)
                .text(message)
                .build();

        try {
            telegramClient.execute(sendMessage);
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }
}
