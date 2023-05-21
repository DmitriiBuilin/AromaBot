package tutorial;

import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.CopyMessage;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.methods.updatingmessages.EditMessageReplyMarkup;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import org.telegram.telegrambots.meta.api.methods.BotApiMethod;


import java.util.List;
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

        var next = InlineKeyboardButton.builder() // Button 1
                .text("Next ➡\uFE0F").callbackData("next")
                .build();

        var back = InlineKeyboardButton.builder() // Button 2
                .text("Back ⬅\uFE0F").callbackData("back")
                .build();

        var url = InlineKeyboardButton.builder()
                .text("Web-site \uD83D\uDDA5")
                .url("http://project7291932.tilda.ws/")
                .build();

        var copy = InlineKeyboardButton.builder()
                .text("Copy \uD83D\uDCD1")
                .callbackData("copy")
                .build();

        InlineKeyboardMarkup keyboard_1; // Initialize Keyboard
        InlineKeyboardMarkup keyboard_2;
        keyboard_1 = InlineKeyboardMarkup.builder()
                .keyboardRow(List.of(copy))
                .keyboardRow(List.of(next)).build();
        keyboard_2 = InlineKeyboardMarkup.builder()
                .keyboardRow(List.of(url))
                .keyboardRow(List.of(back))
                .build();


//        copyMessage(id, update.getMessage().getMessageId());   // Echo Bot
        System.out.println(user.getUserName() + " wrote " + msg.getText());

        if (update.hasCallbackQuery()) {
            String callbackData = update.getCallbackQuery().getData();
            // Handle the callback data and perform the desired action
            if (callbackData.equals("your_button_callback_data")) {
                // Button with "your_button_callback_data" was clicked, perform action
                // ...
            }
        }


        if(msg.isCommand()){
            if(msg.getText().equals("/start"))
                sendMenu(id, "<b>Выберите действие \uD83D\uDE0E</b>", keyboard_1);
            else if (msg.getText().equals("/help"))
                sendMenu(id, "<b>Выберите действие \uD83D\uDE0E</b>", keyboard_2);

            return;            //We don't want to echo commands, so we exit
        }
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


    public void copyMessage(Long who, Integer msgId){
        CopyMessage cm = CopyMessage.builder()
                .fromChatId(who.toString())  //We copy from the user
                .chatId(who.toString())      //And send it back to him
                .messageId(msgId)            //Specifying what message
                .build();
        try {
            execute(cm);
        } catch (TelegramApiException e) {
            throw new RuntimeException(e);
        }
    }

    public void sendMenu(Long who, String txt, InlineKeyboardMarkup kb){
        SendMessage sm = SendMessage.builder().chatId(who.toString())
                .parseMode("HTML").text(txt)
                .replyMarkup(kb).build();

        try {
            execute(sm);
        } catch (TelegramApiException e) {
            throw new RuntimeException(e);
        }
    }

    public void clickButton(String chatId, int messageId) {
        EditMessageReplyMarkup editMessageReplyMarkup = new EditMessageReplyMarkup()
                .setChatId(chatId)
                .setMessageId(messageId)
                .setReplyMarkup(null);  // Set the new markup or null to remove the buttons
        try {
            execute(editMessageReplyMarkup);
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }
}
