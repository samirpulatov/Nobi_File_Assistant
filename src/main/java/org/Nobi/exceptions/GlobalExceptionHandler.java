package org.Nobi.exceptions;


import org.Nobi.services.MessageSender;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.Update;


@Component
public class GlobalExceptionHandler {

    private static final Logger LOGGER = LoggerFactory.getLogger(GlobalExceptionHandler.class);


    private final MessageSender messageSender;

    public GlobalExceptionHandler(MessageSender messageSender) {
        this.messageSender = messageSender;
    }

    public void handleException(Exception e, Update update) {
        var userId = extractUserID(update);
        LOGGER.error(
                "Error while processing update. user_id = {}, message = {}",
                userId,
                e.getMessage(),
                e
        );

        messageSender.buildSendMessage(update.getMessage().getChatId(),"Извините, но на данный момент Nobi не работает. Попробуйте снова через некоторое время!");
    }

    private Long extractUserID(Update update) {
        if(update.hasMessage()) {
            return update.getMessage().getFrom().getId();
        }
        return null;
    }
}
