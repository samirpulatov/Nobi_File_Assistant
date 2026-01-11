package org.Nobi.documents;



import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.botapimethods.BotApiMethod;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Document;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardRow;

import java.util.List;

@Component
public class PdfHandler implements FileHandler {

    @Override
    public boolean canHandle(String fileName) {

        return fileName.contains("pdf") || fileName.contains("PDF");
    }

    @Override
    public List<BotApiMethod<?>> handle(Update update) {

        Document document = update.getMessage().getDocument();
        String file_name = document.getFileName();
        Long chat_id = update.getMessage().getChatId();

        return List.of(introMessage(chat_id,file_name));
    }


    private SendMessage introMessage(Long chat_id, String file_name) {
        String text = "Отлично. Ваш файл был успешно загружен, теперь выберите нужную Вам функцию и нажмите на соответствующую кнопку!";


        InlineKeyboardButton pdfBtn = InlineKeyboardButton.builder()
                .text("Конвертация в WORD\u200B\u200B\uD83D\uDCC4\u200B")
                .callbackData("PDF_TO_WORD")
                .build();

        InlineKeyboardButton webpBtn = InlineKeyboardButton.builder()
                .text("Конвертация в TEXT")
                .callbackData("PDF_TO_TEXT")
                .build();

        InlineKeyboardButton pngBtn = InlineKeyboardButton.builder()
                .text("Конвертация изображений в формат PNG🖼")
                .callbackData("PDF_TO_PNG")
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
