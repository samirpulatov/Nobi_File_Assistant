package org.Nobi.services;

import lombok.Setter;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.methods.botapimethods.BotApiMethod;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboard;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardRow;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import org.telegram.telegrambots.meta.generics.TelegramClient;


@Setter
@Service
public class MessageSender {

    private TelegramClient telegramClient;

    public MessageSender(TelegramClient telegramClient){
        this.telegramClient = telegramClient;
    }
    public void execute(BotApiMethod<?> botApiMethod) {
        try {
            telegramClient.execute(botApiMethod);
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }
}
