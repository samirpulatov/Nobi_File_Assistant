package org.Nobi.services;

import org.Nobi.dto.UserFileSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.methods.GetFile;
import org.telegram.telegrambots.meta.api.methods.send.SendDocument;
import org.telegram.telegrambots.meta.api.objects.Document;
import org.telegram.telegrambots.meta.api.objects.File;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.util.HashMap;
import java.util.Map;

@Service
public class ResponseHandler {


    private final static Logger LOGGER = LoggerFactory.getLogger(ResponseHandler.class);

    // save files for the user during the session to perform actions on the file
    Map<Long, UserFileSession> userFiles = new HashMap<>();

    //service to check the users
    private final UserService userService;

    //service to convert files
    private final FileService fileService;

    //router is used to go through all commands or file operations and choose the correct option
    private final CommandRouter commandRouter;
    private final FileRouter fileRouter;

    //messageSender is used to send objects of type BotApiMethod<?>
    private final MessageSender messageSender;
    //fileSender is used to send objects of type SendMediaBotMethod
    private final FileSender fileSender;

    public ResponseHandler(UserService userService, FileService fileService, CommandRouter commandRouter, FileRouter fileRouter, MessageSender messageSender, FileSender fileSender) {
        this.userService = userService;
        this.fileService = fileService;
        this.commandRouter = commandRouter;
        this.fileRouter = fileRouter;
        this.messageSender = messageSender;
        this.fileSender = fileSender;
    }

    public void handleResponse(Update update) {
        LOGGER.info("Received Update {}", update);
        LOGGER.info("Handle Response {}", update);

        //check for user if there is a new update in form of a message,document and etc
        if(update.getMessage()!=null) userService.initIfNeeded(update); // check if user exists and if not then save him

        //if update has text then
        if(update.getMessage()!=null && update.getMessage().hasText()) {
            LOGGER.info("Handling update with some text");
            commandRouter.routeReceivedUpdate(update)
                    .forEach(messageSender::execute);
        }
        //if update has document then
        else if(update.getMessage()!=null && update.getMessage().hasDocument()) {
            LOGGER.info("Handling update with some document");

            Long chat_id = update.getMessage().getChatId();
            Document document = update.getMessage().getDocument();

            // save the file in userSession
            userFiles.put(chat_id,new UserFileSession(document));

            fileRouter.askForOption(update)
                      .forEach(messageSender::execute);

        }

        // if update has CallBackQuery then perform some action on a File and send it back
        else if(update.hasCallbackQuery()) {
            LOGGER.info("Handling update with callBackQuery");

            Long chat_id = update.getCallbackQuery().getMessage().getChatId();
            String action = update.getCallbackQuery().getData(); //get action that user has chosen like JPG_TO_PDF and etc.

            UserFileSession currentSession = userFiles.get(chat_id); // get the file that was uploaded from this user


            if(currentSession!=null) {
                currentSession.setSelectedAction(action); // set selectedAction and then perform that action. Z.b JPG_TO_PDF

                File tgFile = fileSender.downloadFile(new GetFile(currentSession.getFileId())); // use telegramClient.execute method to get the File
                String file_name = currentSession.getFileName();

                var outputFiles = fileService.convertFileTo(chat_id,tgFile,file_name,action); // convert file to type that user has chosen
                if(outputFiles!=null) {
                    try{
                        for (SendDocument outputFile : outputFiles) {
                            fileSender.sendDocument(outputFile);
                        }
                    } catch (TelegramApiException e) {
                        LOGGER.error("Could not send document");
                        var message = messageSender.buildSendMessage(chat_id,"Извините возникла ошибка во время отправления файла\uD83D\uDE14. Telegram блокирует отправку файлов большого размера, но Вы можете выбрать другой формат и попробовать еще раз! ");
                        messageSender.execute(message);
                    }
                }
            }
        }
    }
}

