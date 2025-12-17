package org.Nobi.commands;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.telegram.telegrambots.meta.api.objects.Update;

public class ListCommand extends CommandHandler {

    private final static Logger LOGGER = LoggerFactory.getLogger(ListCommand.class);

    @Override
    public boolean canHandle(String command) {
        return command.equals("/list");
    }

    public String handle(Update update) {
        LOGGER.info("Received Update {}", update);
        LOGGER.info("LIST command received");
        return """
                Вот список команд, которые я могу выполнить для Вас:
                
                /start — запустить бота и получить приветствие.
                /list — показать этот список команд.
                
                Я всегда готов помочь и сделать Ваш день немного проще! 🚀""";

    }
}
