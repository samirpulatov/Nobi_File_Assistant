package org.Nobi.services;

import lombok.AllArgsConstructor;
import org.Nobi.commands.*;
import org.Nobi.dto.User;
import org.Nobi.enums.UserRole;
import org.Nobi.enums.UserState;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.methods.botapimethods.BotApiMethod;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import org.telegram.telegrambots.meta.generics.TelegramClient;

import java.util.ArrayList;
import java.util.List;

@Service

public class ResponseHandler {
    private final static Logger LOGGER = LoggerFactory.getLogger(ResponseHandler.class);

    private final UserService userService;
    private final UserStateService userStateService;
    private static final List<CommandHandler> commandHandlers = new ArrayList<>();
    private final MessageSender messageSender;
    private final TaskInputHandler taskInputHandler;

    public ResponseHandler(UserService userService, UserStateService userStateService, MessageSender messageSender, TaskInputHandler taskInputHandler){
        this.userService = userService;
        this.userStateService = userStateService;
        this.messageSender = messageSender;
        this.taskInputHandler = taskInputHandler;
    }



    static {
        commandHandlers.add(new StartCommand());
        commandHandlers.add(new DailyTasksCommand());
        commandHandlers.add(new ListCommand());
    }

    public void handleResponse(Update update)  {
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

        UserState userState = userStateService.getUserState(chatId);
        LOGGER.info("UserState of a User with chatId:{} is {}",chatId, userState);
        if(userState == UserState.WAITING_TASKS_INPUT) {
            LOGGER.info("Calling TaskInputHandler");
            BotApiMethod<?> botApiMethod = taskInputHandler.handle(update);
            if(botApiMethod != null) messageSender.execute(botApiMethod);
        }



        commandHandlers.stream()
                    .filter(h -> h.canHandle(message))
                    .findFirst()
                    .map(h -> h.handle(update))
                    .ifPresent(responses ->
                            responses.forEach(messageSender::execute)
                    );

    }
}
