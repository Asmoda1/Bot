package kvv_6_bot;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.json.JSONObject;
import org.telegram.telegrambots.ApiContextInitializer;
import org.telegram.telegrambots.TelegramBotsApi;
import org.telegram.telegrambots.api.methods.send.SendMessage;
import org.telegram.telegrambots.api.methods.send.SendPhoto;
import org.telegram.telegrambots.api.objects.Update;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.exceptions.TelegramApiException;

class MyTelegramBot extends TelegramLongPollingBot {

    // Метод получения команд бота, тут ничего не трогаем
    @Override
    public void onUpdateReceived(Update update) {
        if (update.hasMessage() && update.getMessage().hasText()) {
            SendMessage sendMessage = new SendMessage();
            sendMessage.setChatId(update.getMessage().getChatId());
            sendMessage.setText(doCommand(update.getMessage().getChatId(),
                    update.getMessage().getText()));
            try {
                execute(sendMessage);
            } catch (TelegramApiException e) {
            }
        }
    }

    // Тут задается нужное значение имени бота
    @Override
    public String getBotUsername() {
        return "S1mple_Test_Bot";
    }

    // Тут задается нужное значение токена
    @Override
    public String getBotToken() {
        return "970401857:AAF5T2pCAylxPzf9nvZgHNou00rEEFb5m7Y";
    }

    // Метод обработки команд бота
    public String doCommand(long chatId, String command) {
        if (command.startsWith("/resh")) {
            try {
                sendPhoto(new SendPhoto().setChatId(chatId).setNewPhoto(new File("text.png")));
            } catch (TelegramApiException e) {
            }
            String[] param = command.split(" ");
            if (param.length == 4) {
                return "Ответ: " + getResh(param[1], param[2], param[3]);
            } else {
                return "Используйте команду /resh <значение A> <значение B> <значение X>.";
            }
        }
        try {
            sendPhoto(new SendPhoto().setChatId(chatId).setNewPhoto(new File("text.png")));
        } catch (TelegramApiException e) {
        }
        if (command.startsWith("/start")) {
                return "Вас приветствует бот, созданный учеником ПГУ группы ВТиП-202(с) Куриленко Вадимом. Используйте команду /resh <значение A> <значение B> <значение X>.";
        }
        return "Используйте команду /resh <значение A> <значение B> <значение X>.";
    }

    public String getResh(String a, String b, String x) {
        StringBuilder ans = new StringBuilder();
        double y = 0;
        try {

            if (Float.parseFloat(x) < 4) {
                y = (float) (Math.pow(Float.parseFloat(x), 3) - Float.parseFloat(a) * Float.parseFloat(b));
            } else {
                y = (float) ((Float.parseFloat(x) + 4 * Float.parseFloat(a)) / (Math.pow(Float.parseFloat(a), 2) * Math.pow(Float.parseFloat(b), 2)));
            }
            ans.append(y);
        } catch (NumberFormatException e) {
            ans.append("Введены неверные данные!");
        }
        return ans.toString();
    }

}

public class KVV_6_Bot {

    public static void main(String[] args) {
        ApiContextInitializer.init();
        TelegramBotsApi botsApi = new TelegramBotsApi();
        try {
            botsApi.registerBot(new MyTelegramBot());
        } catch (TelegramApiException e) {
        }
    }

}
