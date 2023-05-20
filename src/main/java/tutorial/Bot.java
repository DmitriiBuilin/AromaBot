package tutorial;

import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.util.Map;

public class Bot extends TelegramLongPollingBot {
    private static final Map<String, String> getenv = System.getenv();
    // getenv.get("TELEGRAM_BOT_NAME"), getenv.get("TELEGRAM_BOT_TOKEN")
    @Override
    public String getBotUsername() {
        return "AromaDRIVEBot";
    }

    @Override
    public String getBotToken() {
        return "6205494677:AAGYWVbw9jahf6lSeGjexx05-Bj61XHZ7L0";
    }

    @Override
    public void onUpdateReceived(Update update) {
        var msg = update.getMessage();
        var user = msg.getFrom();

        var id = user.getId();

        sendText(id, msg.getText()); // echo user
        System.out.println(user.getUserName() + " wrote " + msg.getText());
    }

    public void sendText(Long who, String what){
        SendMessage sm = SendMessage.builder()
                .chatId(who.toString()) //Who are we sending a message to
                .text(what).build();    //Message content
        try {
            execute(sm);                        //Actually sending the message
        } catch (TelegramApiException e) {
            throw new RuntimeException(e);      //Any error will be printed here
        }
    }
}
