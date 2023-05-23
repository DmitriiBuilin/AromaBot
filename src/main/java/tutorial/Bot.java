package tutorial;

import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.AnswerCallbackQuery;
import org.telegram.telegrambots.meta.api.methods.CopyMessage;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.methods.updatingmessages.EditMessageReplyMarkup;
import org.telegram.telegrambots.meta.api.methods.updatingmessages.EditMessageText;
import org.telegram.telegrambots.meta.api.objects.CallbackQuery;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

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
        if (update.hasCallbackQuery()) {
            CallbackQuery callbackQuery = update.getCallbackQuery();
            handleButtonClick(callbackQuery);
        }

        var msg = update.getMessage();
        var user = msg.getFrom();
        var id = user.getId();

        System.out.println(id);

        // Button 1
        var next = InlineKeyboardButton.builder()
                .text("Next ➡\uFE0F").callbackData("next")
                .build();
        // Button 2
        var back = InlineKeyboardButton.builder()
                .text("Back ⬅\uFE0F").callbackData("back")
                .build();
        // Button 3
        var url = InlineKeyboardButton.builder()
                .text("Web-site \uD83D\uDDA5")
                .url("http://project7291932.tilda.ws/")
                .build();
        // Button 4
        var text = InlineKeyboardButton.builder()
                .text("Text \uD83D\uDCD1")
                .callbackData("text")
                .build();

        System.out.println(user.getUserName() + " wrote " + msg.getText());

        InlineKeyboardMarkup keyboard_1; // Initialize Keyboard
        InlineKeyboardMarkup keyboard_2;
        keyboard_1 = InlineKeyboardMarkup.builder()
                .keyboardRow(List.of(text))
                .keyboardRow(List.of(next)).build();
        keyboard_2 = InlineKeyboardMarkup.builder()
                .keyboardRow(List.of(url))
                .keyboardRow(List.of(back))
                .build();

        if(msg.isCommand()){
            if(msg.getText().equals("/start")) {
                sendMenu(id, "<b>Выберите действие \uD83D\uDE0E</b>", keyboard_1);
            }
            else if (msg.getText().equals("/help"))
                sendMenu(id, "<b>Выберите действие \uD83D\uDE0E</b>", keyboard_2);

            return;            //We don't want to echo commands, so we exit
        }
    }

    private void handleButtonClick(CallbackQuery callbackQuery) {
        System.out.println (callbackQuery.getMessage().getChatId());
        System.out.println (callbackQuery.getFrom().getId());

        // Get the data from the button that was clicked
        String buttonData = callbackQuery.getData();
        long id = callbackQuery.getFrom().getId();
        String queryId = callbackQuery.getId();
        int messageId = callbackQuery.getMessage().getMessageId();
        long chatId = callbackQuery.getMessage().getChatId();

        String text = "What is Lorem Ipsum?\n Lorem Ipsum is simply dummy text of the printing and typesetting " +
                "industry. Lorem Ipsum has been the industry's standard dummy text ever since the 1500s, when an " +
                "unknown printer took a galley of type and scrambled it to make a type specimen book. It has " +
                "survived not only five centuries, but also the leap into electronic typesetting, remaining " +
                "essentially unchanged. It was popularised in the 1960s with the release of Letraset sheets " +
                "containing Lorem Ipsum passages, and more recently with desktop publishing software " +
                "like Aldus PageMaker including versions of Lorem Ipsum";

        // Replace the following code with your desired logic based on the button data
        if (buttonData.equals("text")) {
//            System.out.println ("What is Lorem Ipsum?\n" +
//                    "Lorem Ipsum is simply dummy text of the printing and typesetting industry. Lorem Ipsum has been the industry's standard dummy text ever since the 1500s, when an unknown printer took a galley of type and scrambled it to make a type specimen book. It has survived not only five centuries, but also the leap into electronic typesetting, remaining essentially unchanged. It was popularised in the 1960s with the release of Letraset sheets containing Lorem Ipsum passages, and more recently with desktop publishing software like Aldus PageMaker including versions of Lorem Ipsum."
//            );
            sendText(id, text);
        } else if (buttonData.equals("next")) {

        } else if (buttonData.equals("back")) {

        }

        EditMessageReplyMarkup newKb = EditMessageReplyMarkup.builder()
                .chatId(String.valueOf(chatId)).messageId(messageId).build();

        AnswerCallbackQuery close = AnswerCallbackQuery.builder()
                .callbackQueryId(queryId).build();

        try {
            execute(close);
            execute(newKb);
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }

        // Edit the message to remove the inline keyboard
//        EditMessageReplyMarkup editMessageReplyMarkup = new EditMessageReplyMarkup()
//                .setChatId(chatId)
//                .setMessageId(messageId)
//                .setReplyMarkup(new InlineKeyboardMarkup());
//
//        try {
//            // Execute the edit message request
//            execute(editMessageReplyMarkup);
//        } catch (TelegramApiException e) {
//            e.printStackTrace();
//        }
    }

    public void sendText(Long who, String what){
        SendMessage sm = SendMessage.builder()
                .chatId(who.toString())
                .text(what).build();
        try {
            execute(sm);
        } catch (TelegramApiException e) {
            throw new RuntimeException(e);
        }
    }

    //        copyMessage(id, update.getMessage().getMessageId());   // Echo Bot
    public void copyMessage(Long who, Integer msgId){
        CopyMessage cm = CopyMessage.builder()
                .fromChatId(who.toString())
                .chatId(who.toString())
                .messageId(msgId)
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

    private void buttonTap(Long id, String queryId, String data, int msgId) {

        EditMessageText newTxt = EditMessageText.builder()
                .chatId(id.toString())
                .messageId(msgId).text("").build();

        EditMessageReplyMarkup newKb = EditMessageReplyMarkup.builder()
                .chatId(id.toString()).messageId(msgId).build();

        if(data.equals("next")) {
            newTxt.setText("MENU 2");
//            newKb.setReplyMarkup();
        } else if(data.equals("back")) {
            newTxt.setText("MENU 1");
//            newKb.setReplyMarkup();
        }

        AnswerCallbackQuery close = AnswerCallbackQuery.builder()
                .callbackQueryId(queryId).build();

        try {
            execute(close);
            execute(newTxt);
            execute(newKb);
        } catch (TelegramApiException e) {
            throw new RuntimeException(e);
        }

    }
}
