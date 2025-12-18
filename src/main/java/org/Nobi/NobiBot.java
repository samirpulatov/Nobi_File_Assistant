package org.Nobi;

import jakarta.annotation.PostConstruct;
import org.Nobi.services.CallbackService;
import org.Nobi.services.ResponseHandler;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.client.okhttp.OkHttpTelegramClient;
import org.telegram.telegrambots.longpolling.interfaces.LongPollingUpdateConsumer;
import org.telegram.telegrambots.longpolling.starter.SpringLongPollingBot;
import org.telegram.telegrambots.longpolling.util.LongPollingSingleThreadUpdateConsumer;
import org.telegram.telegrambots.meta.api.objects.CallbackQuery;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.generics.TelegramClient;

@Component
public class NobiBot implements SpringLongPollingBot, LongPollingSingleThreadUpdateConsumer {

    private final ResponseHandler responseHandler;
    private final CallbackService callbackService;
    private TelegramClient telegramClient;

    public NobiBot(ResponseHandler responseHandler, CallbackService callbackService) {
        this.responseHandler = responseHandler;
        this.callbackService = callbackService;
    }


    @Value("${telegram.bot.token}")
    private String botToken;

    @PostConstruct
    public void init() {
        this.telegramClient = new OkHttpTelegramClient(botToken);
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
        if (update.hasCallbackQuery()) {
            callbackService.handleCallback(update);
        } else {
            responseHandler.handleResponse(update);
        }
    }
}
