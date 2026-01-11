package org.Nobi.commands;

import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.botapimethods.BotApiMethod;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;

import java.util.List;

@Component
public class StartCommand implements CommandHandler {
    @Override
    public boolean canHandle(String command) {
        return command.equals("/start");
    }

    @Override
    public List<BotApiMethod<?>> handle(Update update) {
        String first_name = update.getMessage().getFrom().getFirstName();
        Long chat_id = update.getMessage().getChatId();
        return List.of(
                introMessage(chat_id,first_name)
        );
    }

    private SendMessage introMessage(Long chat_id, String first_name) {

        return SendMessage.builder()
                .chatId(chat_id)
                .text(
                        "Здравствуйте, " + first_name + " 👋.\n\n" +
                                "Меня зовут Nobi 👽 — Ваш персональный помощник в Telegram. " +
                                "Я помогу Вам работать с файлами и документами прямо здесь: " +
                                "просматривать, редактировать и управлять ими удобно и быстро. "+
                                "Загрузите файл сюда для начала работы."
                )
                .build();
    }
}
