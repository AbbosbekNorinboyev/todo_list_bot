package uz.pdp.java1.util;

import com.vdurmont.emoji.EmojiParser;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class InlineButtonUtil {
    public static InlineKeyboardButton button(String text, String callbackData) {
        InlineKeyboardButton button = new InlineKeyboardButton();
        button.setText(text);
        button.setCallbackData(callbackData);
        return button;
    }

    public static List<InlineKeyboardButton> row1(InlineKeyboardButton... inlineKeyboardButtons) {
        List<InlineKeyboardButton> row1 = new ArrayList<>();
        row1.addAll(Arrays.asList(inlineKeyboardButtons));
        return row1;
    }

    public static List<InlineKeyboardButton> row2(InlineKeyboardButton... inlineKeyboardButtons) {
        List<InlineKeyboardButton> row2 = new ArrayList<>();
        row2.addAll(Arrays.asList(inlineKeyboardButtons));
        return row2;
    }

    public static List<List<InlineKeyboardButton>> rowList(List<InlineKeyboardButton>... rows) {
        List<List<InlineKeyboardButton>> rowList = new ArrayList<>();
        rowList.addAll(Arrays.asList(rows));
        return rowList;
    }

    public static InlineKeyboardMarkup inlineKeyboardMarkup(List<List<InlineKeyboardButton>> collection) {
        InlineKeyboardMarkup inlineKeyboardMarkup = new InlineKeyboardMarkup();
        inlineKeyboardMarkup.setKeyboard(collection);
        return inlineKeyboardMarkup;
    }

    public static InlineKeyboardButton button(String text, String callbackData, String emoji) {
        String emojiText = EmojiParser.parseToUnicode(emoji + " " + text);
        InlineKeyboardButton button = new InlineKeyboardButton();
        button.setText(emojiText);
        button.setCallbackData(callbackData);
        return button;
    }
}
