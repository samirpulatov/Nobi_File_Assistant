package org.Nobi;


import org.Nobi.services.ResponseHandler;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.longpolling.interfaces.LongPollingUpdateConsumer;
import org.telegram.telegrambots.longpolling.starter.SpringLongPollingBot;
import org.telegram.telegrambots.longpolling.util.LongPollingSingleThreadUpdateConsumer;
import org.telegram.telegrambots.meta.api.objects.Update;

@Component
public class NobiBot implements SpringLongPollingBot, LongPollingSingleThreadUpdateConsumer {

    private final ResponseHandler responseHandler;



    @Value("${telegram.bot.token}")
    private String botToken;

    public NobiBot(ResponseHandler responseHandler) {
        this.responseHandler = responseHandler;

    }

    @Override
    public String getBotToken() {
        return botToken;
    }

    @Override
    public LongPollingUpdateConsumer getUpdatesConsumer() {
        return this;
    }

    @Override
    public void consume(Update update) {
            responseHandler.handleResponse(update);
    }


}
