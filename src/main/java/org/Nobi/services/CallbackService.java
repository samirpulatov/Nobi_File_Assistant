package org.Nobi.services;

import org.Nobi.commands.CallBackHandler;
import org.Nobi.commands.DailyPermissionCallbackHandler;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.objects.CallbackQuery;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.generics.TelegramClient;

import java.util.ArrayList;
import java.util.List;

@Service
public class CallbackService {

    private final List<CallBackHandler> callBackHandlers = new ArrayList<>();
    private final MessageSender messageSender;
    public CallbackService(MessageSender messageSender)
    {
        this.messageSender = messageSender;
    }

    public void setTelegramClient(TelegramClient telegramClient)
    {
        messageSender.setTelegramClient(telegramClient);
    }
    public void handleCallback(Update update){

        CallbackQuery callbackQuery = update.getCallbackQuery();
        String message = callbackQuery.getData().toString();
        callBackHandlers.add(new DailyPermissionCallbackHandler());
        callBackHandlers.stream()
                .filter(h -> h.canHandle(message))
                .findFirst()
                .map(h -> h.onCall(callbackQuery))
                .ifPresent(responses ->
                        responses.forEach(messageSender::execute));

    }
}
