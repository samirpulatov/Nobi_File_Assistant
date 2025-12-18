package org.Nobi.commands;

import org.telegram.telegrambots.meta.api.methods.botapimethods.BotApiMethod;
import org.telegram.telegrambots.meta.api.objects.CallbackQuery;
import org.telegram.telegrambots.meta.api.objects.Update;

import java.util.List;

public interface CallBackHandler {
    boolean canHandle(String callBackData);
    List<BotApiMethod<?>> onCall(CallbackQuery callbackQuery);
}
