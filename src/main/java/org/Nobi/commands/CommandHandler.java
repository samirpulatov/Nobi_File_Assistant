package org.Nobi.commands;

import org.telegram.telegrambots.meta.api.objects.Update;

public abstract class CommandHandler {


    public boolean canHandle(String command) {
        return false;
    }


    public String handle(Update update) {
        return update.getMessage().getText();
    }

}
