package org.Nobi.services;

import org.Nobi.commands.CommandHandler;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.methods.botapimethods.BotApiMethod;
import org.telegram.telegrambots.meta.api.objects.Update;

import java.util.List;

@Service
public class CommandRouter {
    private final List<CommandHandler> commandHandlers;


    public CommandRouter(List<CommandHandler> commandHandlers) {
        this.commandHandlers = commandHandlers;
    }

    //handle update
    public List<BotApiMethod<?>> routeReceivedUpdate(Update update) {


        return routeCommand(update); // if update has text then check for a commands


    }

    private List<BotApiMethod<?>> routeCommand(Update update) {
        String message = update.getMessage().getText();

        return commandHandlers.stream()
                .filter(h -> h.canHandle(message))
                .findFirst()
                .map(h -> h.handle(update))
                .orElseThrow();
    }
}
