package org.Nobi.services;

import org.telegram.telegrambots.meta.api.objects.Update;

public class StartCommand extends CommandHandler {


    @Override
    public boolean canHandle(String command) {
        return command.equals("/start");
    }

    public String handle(Update update) {
        String firstName = update.getMessage().getFrom().getFirstName();
        return "Здравствуйте, "+ firstName+" \uD83D\uDD90.\n" +
                "Я — Nobi \uD83E\uDD16." +
                "Помогаю вам сохранять фокус на важном: фиксирую события, создаю напоминания и формирую аккуратное расписание.\n" +
                "Чтобы узнать больше, введите /list.";
    }
}
