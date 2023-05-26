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

        // Initialize Buttons
        var games = InlineKeyboardButton.builder()
                .text("Арома игры \uD83C\uDFB2").callbackData("games")
                .build();
        var oils = InlineKeyboardButton.builder()
                .text("Арома масла \uD83C\uDF31").callbackData("oils")
                .build();

        var next = InlineKeyboardButton.builder()
                .text("Next ➡\uFE0F").callbackData("next")
                .build();

        var back = InlineKeyboardButton.builder()
                .text("Back ⬅\uFE0F").callbackData("back")
                .build();

        var url = InlineKeyboardButton.builder()
                .text("Web-site \uD83D\uDDA5")
                .url("http://project7291932.tilda.ws/")
                .build();

        var text = InlineKeyboardButton.builder()
                .text("Text \uD83D\uDCD1")
                .callbackData("text")
                .build();

        System.out.println(user.getUserName() + " wrote " + msg.getText());

        InlineKeyboardMarkup keyboard_1; // Initialize Keyboard
        InlineKeyboardMarkup keyboard_2;
        keyboard_1 = InlineKeyboardMarkup.builder()
                .keyboardRow(List.of(games))
                .keyboardRow(List.of(oils))
                .build();
        keyboard_2 = InlineKeyboardMarkup.builder()
                .keyboardRow(List.of(url))
                .keyboardRow(List.of(back))
                .build();

        if(msg.isCommand()){
            if(msg.getText().equals("/start")) {
                sendMenu(id, "<b>Выберите раздел \uD83D\uDE0E</b>", keyboard_1);
            }
            else if (msg.getText().equals("/help"))
                sendMenu(id, "<b>Выберите действие \uD83D\uDE0E</b>", keyboard_2);

            return;            //We don't want to echo commands, so we exit
        }
    }

    String next = String.valueOf(InlineKeyboardButton.builder()
            .text("Next").callbackData("next")
            .build());
    private InlineKeyboardMarkup keyboardM1;




    private void handleButtonClick(CallbackQuery callbackQuery) {
//        System.out.println (callbackQuery.getMessage().getChatId());

        // Get the data from the button that was clicked
        String buttonData = callbackQuery.getData();
        long id = callbackQuery.getFrom().getId();
        String queryId = callbackQuery.getId();
        int messageId = callbackQuery.getMessage().getMessageId();
        long chatId = callbackQuery.getMessage().getChatId();

        String text = "What is Lorem Ipsum?\n Lorem Ipsum is simply dummy text of the printing and typesetting " +
                "industry. Lorem Ipsum has been the industry's standard dummy text ever since the 1500s, when an ";

        EditMessageReplyMarkup newKb = EditMessageReplyMarkup.builder()
                .chatId(String.valueOf(chatId)).messageId(messageId).build();

        AnswerCallbackQuery close = AnswerCallbackQuery.builder()
                .callbackQueryId(queryId).build();

        EditMessageText newTxt = EditMessageText.builder()
                .chatId(String.valueOf(chatId))
                .messageId(messageId).text("").build();

//        DeleteMessage delMsg = DeleteMessage.builder().build();


// Buttons for new keyboard
        var games = InlineKeyboardButton.builder()
                .text("Арома игры \uD83C\uDFB2").callbackData("games")
                .build();
        var oils = InlineKeyboardButton.builder()
                .text("Арома масла \uD83C\uDF31").callbackData("oils")
                .build();
        var game_1 = InlineKeyboardButton.builder()
                .text("Цветик-семицветик \uD83C\uDF38")
                .callbackData("game_1")
                .build();
        var game_2 = InlineKeyboardButton.builder()
                .text("Остров сокровищ \uD83C\uDFF4\u200D☠\uFE0F")
                .callbackData("game_2")
                .build();
        var back = InlineKeyboardButton.builder()
                .text("Back ⬅\uFE0F")
                .callbackData("back")
                .build();

        InlineKeyboardMarkup startKeyboard;
        InlineKeyboardMarkup gamesKeyboard;
        InlineKeyboardMarkup oilsKeyboard;
        InlineKeyboardMarkup backKeyboard;

        startKeyboard = InlineKeyboardMarkup.builder()
                .keyboardRow(List.of(games))
                .keyboardRow(List.of(oils))
                .build();
        gamesKeyboard = InlineKeyboardMarkup.builder()
                .keyboardRow(List.of(game_1))
                .keyboardRow(List.of(game_2))
                .keyboardRow(List.of(back))
                .build();
        oilsKeyboard = InlineKeyboardMarkup.builder()
                .keyboardRow(List.of(back))
                .build();
        backKeyboard = InlineKeyboardMarkup.builder()
                .keyboardRow(List.of(back))
                .build();

        // Replace the following code with your desired logic based on the button data
        if (buttonData.equals("games")) {
//            sendText(id, text);
            newKb.setReplyMarkup(gamesKeyboard);
        } else if (buttonData.equals("oils")) {
            sendText(id, "Здесь будет раздел об аромамаслах");
            newKb.setReplyMarkup(oilsKeyboard);
        } else if (buttonData.equals("game_1")) {
            sendText(id, "Игра 'Цветик Семицветик'. \n - описание игры \n - представление ведущих \n - график проведения игр \n - запись (?)");
            newKb.setReplyMarkup(backKeyboard);
        } else if (buttonData.equals("game_2")) {
            sendText(id, "Игра 'Остров сокровищ'. \n - описание игры \n - представление ведущих \n - график проведения игр \n - запись (?)");
            newKb.setReplyMarkup(backKeyboard);
        } else if (buttonData.equals("back")) {
            newKb.setReplyMarkup(startKeyboard);
        }

        try {
            execute(close);
            execute(newKb);
//            execute(newTxt);
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
}
