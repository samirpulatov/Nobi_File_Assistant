package org.Nobi.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.client.okhttp.OkHttpTelegramClient;
import org.telegram.telegrambots.meta.api.methods.botapimethods.BotApiMethod;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import org.telegram.telegrambots.meta.generics.TelegramClient;

@Service
public class MessageSender {

    @Autowired
    private  TelegramClient telegramClient;

    public void execute(BotApiMethod<?> botApiMethod) {
        try {
            telegramClient.execute(botApiMethod);
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }

    public SendMessage buildSendMessage(Long chat_id,String text) {

        return SendMessage.builder()
                .chatId(chat_id)
                .text(text)
                .build();
    }
}
