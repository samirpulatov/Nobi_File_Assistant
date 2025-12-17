package org.Nobi.services;

import org.Nobi.commands.CommandHandler;
import org.Nobi.commands.ListCommand;
import org.Nobi.commands.StartCommand;
import org.Nobi.dto.User;
import org.Nobi.enums.UserRole;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.generics.TelegramClient;

import java.util.ArrayList;
import java.util.List;

@Service
public class ResponseHandler {
    private final static Logger LOGGER = LoggerFactory.getLogger(ResponseHandler.class);

    private final UserService userService;
    private final List<CommandHandler> commandHandlers = new ArrayList<>();
    private final MessageSender messageSender;

    public ResponseHandler(UserService userService, MessageSender messageSender){
        this.userService = userService;
        this.messageSender = messageSender;
    }

    public void setTelegramClient(TelegramClient telegramClient) {
        messageSender.setTelegramClient(telegramClient);
    }


    public void handleResponse(Update update) {
        LOGGER.info("Received Update {}", update);
        LOGGER.info("Handle Response {}", update);
        String message = update.getMessage().getText();
        Long chatId = update.getMessage().getChatId();

        if(!userService.userExists(chatId)){
            String userName = update.getMessage().getFrom().getUserName();
            String firstName = update.getMessage().getFrom().getFirstName();
            String lastName = update.getMessage().getFrom().getLastName();
            UserRole userRole = UserRole.USER;
            userService.saveUser(new User(chatId,userName,firstName,lastName,userRole));
        }
        commandHandlers.add(new StartCommand());
        commandHandlers.add(new ListCommand());
        commandHandlers.stream()
                .filter(h -> h.canHandle(message))
                .findFirst()
                .map(h -> h.handle(update))
                .ifPresent(response -> messageSender.sendMessage(chatId,response));

    }
}
