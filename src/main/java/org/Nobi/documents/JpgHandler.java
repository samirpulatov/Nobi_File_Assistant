package org.Nobi.documents;

import org.Nobi.services.ResponseHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.botapimethods.BotApiMethod;
import org.telegram.telegrambots.meta.api.methods.send.SendDocument;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Document;
import org.telegram.telegrambots.meta.api.objects.InputFile;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardRow;

import java.util.List;

@Component
public class JpgHandler implements FileHandler {

    private final static Logger LOGGER = LoggerFactory.getLogger(JpgHandler.class);

    @Override
    public boolean canHandle(String fileName) {

        return fileName.contains("jpg") || fileName.contains("JPG") || fileName.contains("jpeg") || fileName.contains("JPEG");
    }

    @Override
    public List<BotApiMethod<?>>handle(Update update) {

        Document document = update.getMessage().getDocument();
        String file_name = document.getFileName();
        Long chat_id = update.getMessage().getChatId();

        return List.of(introMessage(chat_id,file_name));

    }

    private SendMessage introMessage(Long chat_id, String file_name) {
        String text = "Отлично. Ваш файл был успешно загружен, теперь выберите нужную Вам функцию и нажмите на соответствующую кнопку!";


        InlineKeyboardButton pdfBtn = InlineKeyboardButton.builder()
                .text("Конвертация в PDF📄")
                .callbackData("JPG_TO_PDF")
                .build();

        InlineKeyboardButton webpBtn = InlineKeyboardButton.builder()
                .text("Конвертация в WEBP🌐")
                .callbackData("JPG_TO_WEBP")
                .build();

        InlineKeyboardButton pngBtn = InlineKeyboardButton.builder()
                .text("Конвертация в PNG🖼")
                .callbackData("JPG_TO_PNG")
                .build();



        return SendMessage.builder()
                .chatId(chat_id)
                .text(text)
                .replyMarkup(InlineKeyboardMarkup.builder()
                        .keyboardRow(new InlineKeyboardRow(pdfBtn))
                        .keyboardRow(new InlineKeyboardRow(webpBtn))
                        .keyboardRow(new InlineKeyboardRow(pngBtn))
                        .build()
                )
                .build();
    }


}
