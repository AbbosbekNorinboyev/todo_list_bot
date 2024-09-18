package uz.pdp.java1.controller;

import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.methods.send.SendPhoto;
import org.telegram.telegrambots.meta.api.methods.send.SendVideo;
import org.telegram.telegrambots.meta.api.methods.updatingmessages.EditMessageText;
import org.telegram.telegrambots.meta.api.objects.InputFile;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;
import uz.pdp.java1.enums.CodeMessageType;
import uz.pdp.java1.todo.CodeMessage;
import uz.pdp.java1.util.InlineButtonUtil;

import java.util.ArrayList;
import java.util.List;

public class GeneralController {
    public CodeMessage handle(String text, Long chatId, Integer messageId) {
        CodeMessage codeMessage = new CodeMessage();
        SendMessage sendMessage = new SendMessage();
        sendMessage.setChatId(chatId);
        codeMessage.setSendMessage(sendMessage);

        if (text.equalsIgnoreCase("/start")) {
            sendMessage.setText("<b>Assalomu alaykum.</b>\n<i>TodoList botiga xush kelibsiz.</i>");
            sendMessage.setParseMode("HTML");

            InlineKeyboardButton menu = InlineButtonUtil.button("Go to menu", "Menu");
            InlineKeyboardButton test = InlineButtonUtil.button("Go to test", "Test");
            List<InlineKeyboardButton> row1 = InlineButtonUtil.row1(menu, test);
            InlineKeyboardButton second = InlineButtonUtil.button("Go to second test", "Second test");
            List<InlineKeyboardButton> row2 = InlineButtonUtil.row2(second);
            List<List<InlineKeyboardButton>> rowList = InlineButtonUtil.rowList(row1, row2);
            InlineKeyboardMarkup inlineKeyboardMarkup = InlineButtonUtil.inlineKeyboardMarkup(rowList);
            sendMessage.setReplyMarkup(inlineKeyboardMarkup);

            codeMessage.setCodeMessageType(CodeMessageType.MESSAGE);

        } else if (text.equalsIgnoreCase("/help")) {
            String msg = "TodoList yordam oynasi." +
                    "\n*Siz bu bot da qilish kerak bo'lgan ishlaringiz jadvalini tuzishingiz mumkin.*" +
                    "\nMalumot uchun videoni [YouTobe kanal](https://www.youtube.com/@codeuz8122/playlists) ko'ring.";
            sendMessage.setText(msg);
            sendMessage.setParseMode("Markdown");
            sendMessage.disableWebPagePreview();

            InputFile inputFileVideo = new InputFile();
            inputFileVideo.setMedia("BAACAgIAAxkBAAIBg2R9x1fZZQVIMuDTwZlB9PfxO-OZAAIULwACTP7pSyDP5dUkxcjBLwQ");
            SendVideo sendVideo = new SendVideo();
            sendVideo.setChatId(chatId);
            sendVideo.setVideo(inputFileVideo);

            InputFile inputFilePhoto = new InputFile();
            inputFilePhoto.setMedia("AgACAgIAAxkBAAIBgWR9x0kdKHBpcEABhwMaa2Qrj6sjAALxxjEbAgzoS7j4TFy5eA38AQADAgADcwADLwQ");
            SendPhoto sendPhoto = new SendPhoto();
            sendPhoto.setChatId(chatId);
            sendPhoto.setPhoto(inputFilePhoto);

            codeMessage.setSendMessage(sendMessage);
            codeMessage.setSendVideo(sendVideo);
            codeMessage.setSendPhoto(sendPhoto);
            codeMessage.setCodeMessageType(CodeMessageType.MESSAGE_VIDEO_PHOTO);

        } else if (text.equalsIgnoreCase("/setting")) {
            sendMessage.setText("<u>Settinglar hali majud emas.</u>");
            sendMessage.setParseMode("HTML");
            codeMessage.setCodeMessageType(CodeMessageType.MESSAGE);

        } else if (text.equalsIgnoreCase("Menu")) {
            EditMessageText editMessageText = new EditMessageText();
            editMessageText.setChatId(chatId);
            editMessageText.setMessageId(messageId);
            editMessageText.setText("<b>Asosiy menu</b>");
            editMessageText.setParseMode("HTML");

            InlineKeyboardButton todo = new InlineKeyboardButton();
            todo.setText("\uD83D\uDCCB " + "Todo List");
            todo.setCallbackData("/todo/list");
            InlineKeyboardButton create = new InlineKeyboardButton();
            create.setText("➕ " + "Create New");
            create.setCallbackData("/todo/create");
            List<InlineKeyboardButton> rowTodo = new ArrayList<>();
            rowTodo.add(todo);
            List<InlineKeyboardButton> rowCreate = new ArrayList<>();
            rowCreate.add(create);
            List<List<InlineKeyboardButton>> collectionTodo = new ArrayList<>();
            collectionTodo.add(rowTodo);
            collectionTodo.add(rowCreate);
            InlineKeyboardMarkup inlineKeyboardMarkupTodo = new InlineKeyboardMarkup();
            inlineKeyboardMarkupTodo.setKeyboard(collectionTodo);
            editMessageText.setReplyMarkup(inlineKeyboardMarkupTodo);

            codeMessage.setCodeMessageType(CodeMessageType.EDIT);
            codeMessage.setEditMessageText(editMessageText);
        }
        return codeMessage;
    }
}
