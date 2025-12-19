package org.Nobi.commands;

import org.Nobi.enums.UserState;
import org.Nobi.services.UserStateService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.telegram.telegrambots.meta.api.methods.AnswerCallbackQuery;
import org.telegram.telegrambots.meta.api.methods.botapimethods.BotApiMethod;
import org.telegram.telegrambots.meta.api.methods.updatingmessages.EditMessageText;
import org.telegram.telegrambots.meta.api.objects.CallbackQuery;
import org.telegram.telegrambots.meta.api.objects.Update;

import java.util.List;

public class DailyPermissionCallbackHandler implements CallBackHandler {

    private final static Logger LOGGER = LoggerFactory.getLogger(DailyPermissionCallbackHandler.class);

    private final UserStateService userStateService;

    public DailyPermissionCallbackHandler(UserStateService userStateService) {
        this.userStateService = userStateService;
    }


    @Override
    public boolean canHandle(String callBackData) {
        return callBackData.equals("yes") || callBackData.equals("no");
    }

    @Override
    public List<BotApiMethod<?>> onCall(CallbackQuery callbackQuery) {
        Long chatId = callbackQuery.getMessage().getChatId();
        Integer messageId = callbackQuery.getMessage().getMessageId();


        String newText = callbackQuery.getData().equals("yes")? permissionAcceptedText() : permissionDeniedText();

        LOGGER.info("New Permission Accepted Text: " + newText);
        LOGGER.info("Set User with chatId "+chatId+" to state: WAITING_TASKS_INPUT");
        userStateService.setUserState(chatId, UserState.WAITING_TASKS_INPUT);

        return List.of(
                editMessageText(chatId,messageId,newText),
                answerCallbackQuery(callbackQuery.getId())
        );
    }


    private EditMessageText editMessageText(Long chatId,Integer messageId, String text) {
        return EditMessageText.builder()
                .chatId(chatId)
                .messageId(messageId)
                .text(text)
                .build();
    }

    private AnswerCallbackQuery answerCallbackQuery(String callBackId) {
        return AnswerCallbackQuery.builder()
                .callbackQueryId(callBackId)
                .build();
    }

    private String permissionAcceptedText() {
        return
        """
        Отлично! 🎉.
        Я буду присылать Вам напоминания и помогать отслеживать выполнение задач.
        
        Давайте начнём. Перечислите одну или несколько задач, которые Вы хотите выполнять каждый день 📝.
        Например: пробежка 30 мин, почитать книгу, составить расписание на день и так далее.
        
        """;
    }

    private String permissionDeniedText() {
        return
        """
        Хорошо, я вас понял 👍. Я не буду отправлять напоминания, но вы по-прежнему можете пользоваться функцией отслеживания задач
        и отмечать их выполнение в удобное для Вас время. Когда будете готовы — просто перечислите одну или несколько задач, которые Вы хотите выполнять каждый день 📝.
        Например: пробежка 30 мин, почитать книгу, составить расписание на день и так далее.🗂️
        """;
        }
    }
