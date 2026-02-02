package org.Nobi;


import org.Nobi.exceptions.GlobalExceptionHandler;
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
    private final GlobalExceptionHandler globalExceptionHandler;
    @Value("${telegram.bot.token}")
    private String botToken;

    public NobiBot(ResponseHandler responseHandler, GlobalExceptionHandler globalExceptionHandler) {
        this.responseHandler = responseHandler;
        this.globalExceptionHandler = globalExceptionHandler;
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
    public void consume(Update update){
        try{
            responseHandler.handleResponse(update);
        } catch (Exception e) {
            globalExceptionHandler.handleException(e,update);
        }

    }
}
