package uz.pdp.java1;

import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.methods.updatingmessages.EditMessageText;
import org.telegram.telegrambots.meta.api.objects.CallbackQuery;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.User;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.util.ArrayList;
import java.util.List;

public class Main2 {
    public static void main(String[] args) {

    }

    public class MainController extends TelegramLongPollingBot {
        @Override
        public void onUpdateReceived(Update update) {
            SendMessage sendMessage = new SendMessage();
            if (update.hasCallbackQuery()) {
                CallbackQuery callbackQuery = update.getCallbackQuery();
                String data = callbackQuery.getData();
                Message messageCallbackQuery = callbackQuery.getMessage();
                if (data.equalsIgnoreCase("Menu")) {
                    EditMessageText editMessageText = new EditMessageText();
                    editMessageText.setChatId(messageCallbackQuery.getChatId());
                    editMessageText.setMessageId(messageCallbackQuery.getMessageId());
                    editMessageText.setText("<b>Siz menu buttonni bosdingiz.</b>");
                    editMessageText.setParseMode("HTML");

                    InlineKeyboardButton button = new InlineKeyboardButton();
                    button.setText("Inner button");
                    button.setCallbackData("Inner");
                    List<InlineKeyboardButton> innerRow = new ArrayList<>();
                    innerRow.add(button);
                    List<List<InlineKeyboardButton>> innerRowList = new ArrayList<>();
                    innerRowList.add(innerRow);
                    InlineKeyboardMarkup innerInlineKeyboardMarkup = new InlineKeyboardMarkup();
                    innerInlineKeyboardMarkup.setKeyboard(innerRowList);
                    editMessageText.setReplyMarkup(innerInlineKeyboardMarkup);

                    try {
                        execute(editMessageText);
                    } catch (TelegramApiException e) {
                        e.printStackTrace();
                    }
                }
            } else {
                Message message = update.getMessage();
                Integer messageId = message.getMessageId();
                String text = message.getText();
                User userFrom = message.getFrom();
                System.out.println(userFrom.getId());
                Long chatId = message.getChatId();

                System.out.println("User_name: " + userFrom.getFirstName() + "\nUser_username: " + userFrom.getLastName() +
                        "\nMessage: " + text + "\nMessage id: " + messageId +
                        "\nChat id: " + message.getChatId() + "\nId: " + userFrom.getId() +
                        "\nChat: " + message.getChat() +
                        "\nUser from id: " + userFrom.getId() + "\n");

                if (text.equalsIgnoreCase("/start")) {
                    sendMessage.setChatId(chatId);
                    sendMessage.setText("<b>Assalomu alaykum.</b>\n<i>Bot ga xush kelibsiz.</i>");
                    sendMessage.setParseMode("HTML");

                    InlineKeyboardButton menuButton = new InlineKeyboardButton();
                    menuButton.setText("Go to Menu");
                    menuButton.setCallbackData("Menu");
                    InlineKeyboardButton testButton = new InlineKeyboardButton();
                    testButton.setText("Go to Test");
                    testButton.setCallbackData("Test");
                    List<InlineKeyboardButton> row1 = new ArrayList<>();
                    row1.add(menuButton);
                    row1.add(testButton);
                    InlineKeyboardButton test = new InlineKeyboardButton();
                    test.setText("Second TEST");
                    test.setCallbackData("Test two");
                    List<InlineKeyboardButton> row2 = new ArrayList<>();
                    row2.add(test);
                    List<List<InlineKeyboardButton>> rowList = new ArrayList<>();
                    rowList.add(row1);
                    rowList.add(row2);
                    InlineKeyboardMarkup inlineKeyboardMarkup = new InlineKeyboardMarkup();
                    inlineKeyboardMarkup.setKeyboard(rowList);
                    sendMessage.setReplyMarkup(inlineKeyboardMarkup);

                } else if (text.equalsIgnoreCase("/help")) {
                    sendMessage.setChatId(chatId);
                    sendMessage.setText("*Sizga yordam kerakmi?* \n_Bot ni qanday ishlatishni bilmayapsizmi?_" +
                            "\nUnda mana bu videoni ko'ring:" +
                            "\n[YouTobe kanal](https://www.youtube.com/@codeuz8122/playlists)");
                    sendMessage.setParseMode("Markdown");
                } else if (text.equalsIgnoreCase("/setting")) {
                    sendMessage.setChatId(chatId);
                    sendMessage.setText("<u>Settinglar hali majud emas.</u>");
                    sendMessage.setParseMode("HTML");
                } else {
                    sendMessage.setChatId(chatId);
                    sendMessage.setText("_Tushunmadim._");
                    sendMessage.setParseMode("Markdown");
                }
            }

            try {
                execute(sendMessage);
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
}
