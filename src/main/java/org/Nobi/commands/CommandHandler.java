package org.Nobi.commands;

import org.telegram.telegrambots.meta.api.methods.botapimethods.BotApiMethod;
import org.telegram.telegrambots.meta.api.objects.Update;

import java.util.List;

public abstract class CommandHandler {


    public boolean canHandle(String command) {
        return false;
    }


    public List<BotApiMethod<?>> handle(Update update){
        return null;
    }

}
