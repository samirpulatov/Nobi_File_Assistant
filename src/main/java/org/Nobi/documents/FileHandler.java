package org.Nobi.documents;

import org.telegram.telegrambots.meta.api.methods.botapimethods.BotApiMethod;
import org.telegram.telegrambots.meta.api.objects.Update;

import java.util.List;

public interface FileHandler {

    public boolean canHandle(String fileName);

    public List<BotApiMethod<?>> handle(Update update);
}
