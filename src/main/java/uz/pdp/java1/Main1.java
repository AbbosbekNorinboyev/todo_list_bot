package uz.pdp.java1;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import uz.pdp.java1.controller.MainController;
import org.telegram.telegrambots.meta.TelegramBotsApi;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import org.telegram.telegrambots.updatesreceivers.DefaultBotSession;

public class Main1 {
    private static final Logger LOGGER = LoggerFactory.getLogger(Main1.class);
    public static void main(String[] args) {
        LOGGER.info("START");
        try {
            TelegramBotsApi telegramBotsApi = new TelegramBotsApi(DefaultBotSession.class);
            telegramBotsApi.registerBot(new MainController());
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }

//        for (int i = 0; i < 256; i += 1)
//        {
//            System.out.print(String.format(" \u001b[38;2;0;0;%sm %s \u001b[0m ", i, i));
//        }
    }
}
