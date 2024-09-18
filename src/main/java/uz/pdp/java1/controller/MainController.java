package uz.pdp.java1.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.CallbackQuery;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.User;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import uz.pdp.java1.service.FileInfoService;
import uz.pdp.java1.todo.CodeMessage;

public class MainController extends TelegramLongPollingBot {
    private static final Logger LOGGER = LoggerFactory.getLogger(MainController.class);
    private GeneralController generalController;
    private FileInfoService fileInfoService;
    private TodoController todoController;

    public MainController() {
        this.generalController = new GeneralController();
        this.fileInfoService = new FileInfoService();
        this.todoController = new TodoController();
    }

    @Override
    public void onUpdateReceived(Update update) {
        try {
            SendMessage sendMessage = new SendMessage();
            Message message = update.getMessage();
            if (update.hasCallbackQuery()) {
                CallbackQuery callbackQuery = update.getCallbackQuery();
                String data = callbackQuery.getData();
                Message messageCallbackQuery = callbackQuery.getMessage();
                if (data.equalsIgnoreCase("Menu")) {
                    this.sendMsg(this.generalController.handle(data, messageCallbackQuery.getChatId(), messageCallbackQuery.getMessageId()));
                }
                if (data.startsWith("/todo")) {
                    this.sendMsg(this.todoController.handle(data, messageCallbackQuery.getChatId(), messageCallbackQuery.getMessageId()));
                }
            } else if (message != null) {
                String text = message.getText();
                User userFrom = message.getFrom();
                Long chatId = message.getChatId();
                Integer messageId = message.getMessageId();

                LOGGER.info("User_name: " + userFrom.getFirstName() + "\nUser_username: " + userFrom.getLastName() +
                        "\nMessage: " + text + "\nMessage id: " + messageId +
                        "\nChat id: " + message.getChatId() + "\nId: " + userFrom.getId() +
                        "\nChat: " + message.getChat() +
                        "\nUser from id: " + userFrom.getId() + "\n");

                if (text != null) {
                    if (text.equalsIgnoreCase("/start") || text.equalsIgnoreCase("/help")
                            || text.equalsIgnoreCase("/setting")) {
                        this.sendMsg(this.generalController.handle(text, chatId, messageId));
                    } else if (this.todoController.getTodoItemStep().containsKey(message.getChatId()) || text.startsWith("/todo_")) {
                        this.sendMsg(this.todoController.handle(text, chatId, messageId));
                    } else {
                        sendMessage.setChatId(chatId);
                        sendMessage.setText("_Tushunmadim._");
                        sendMessage.setParseMode("Markdown");
                        this.sendMsg(sendMessage);
                    }
                } else {
                    this.sendMsg(this.fileInfoService.getFileInfo(message));
                }
            }
        } catch (Exception e) {
            LOGGER.error(e.getMessage());
            e.printStackTrace();
        }
    }

    public void sendMsg(SendMessage sendMessage) {
        try {
            execute(sendMessage);
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }

    public void sendMsg(CodeMessage codeMessage) {
        try {
            switch (codeMessage.getCodeMessageType()) {
                case MESSAGE:
                    execute(codeMessage.getSendMessage());
                    break;
                case EDIT:
                    execute(codeMessage.getEditMessageText());
                    break;
                case MESSAGE_VIDEO_PHOTO:
                    execute(codeMessage.getSendMessage());
                    execute(codeMessage.getSendVideo());
                    execute(codeMessage.getSendPhoto());
                    break;
                default:
                    break;
            }
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }

    @Override
    public String getBotUsername() {
        return "CodeTestUzBot";
    }

    @Override
    public String getBotToken() {
        return "6070300347:AAFVeU1UZ8FWW85cqHyMDMCENleZYsKRuZk";
    }
}
