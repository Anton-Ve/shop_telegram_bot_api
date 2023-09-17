package bot;

import entities.User2;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.methods.updatingmessages.EditMessageText;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import repository.UserRepository;

import java.util.ArrayList;
import java.util.List;

import static components.BotCommands.*;
import static java.lang.Math.*;


public class TelegramBot extends TelegramLongPollingBot {

    private UserRepository userRepository = new UserRepository();


    public TelegramBot() throws ClassNotFoundException {
    }
    private final Logger logger = LoggerFactory.getLogger(Logger.class);

    @Override
    public String getBotUsername() {
        return "sope_telegram_bot_api";
    }

    @Override
    public String getBotToken() {
        return "6326791650:AAGsnTPqroWivmWVTYMwKYXYqLTWDK2olK0";
    }

    @Override
    public void onUpdateReceived(Update update) {
        // логика которая отвечает за вывод команд на экран
        if (update.hasMessage() && update.getMessage().hasText()) {
            if (update.getMessage().hasText()) {

            }
        }
        // действие при нажатии кнопок
        if (update.hasCallbackQuery()) {
            String callbackStep = update.getCallbackQuery().getData();
            long messageId = update.getCallbackQuery().getMessage().getMessageId();
            long chatId = update.getCallbackQuery().getMessage().getChatId();

            if (callbackStep.equals (LOK_BUTTON)) {
                String text ="https://yandex.ru/maps/org/sp_kompyuter/1052950438/" +
              "\nreviews/?indoorLevel=1&ll=60.597949%2C56.832538&z=17.52";
                //String text = "Местоположение нашего магазина";
                executeEditMessageText(text, chatId, messageId);
                EditMessageText msg = new EditMessageText();
                msg.setChatId(String.valueOf(chatId));
                msg.setText(text);
                msg.setMessageId((int) messageId);

            } else if (callbackStep.equals(DS_BUTTON)) {
                String text ="https://www.sp-computer.ru/";
                //String text = "Описание товаров";
                executeEditMessageText(text, chatId, messageId);
                EditMessageText msg = new EditMessageText();
                msg.setChatId(String.valueOf(chatId));
                msg.setText(text);
                msg.setMessageId((int) messageId);

            }
        }
        //логика вывода команд
        else {
            Integer id = toIntExact(update.getMessage().getChatId());
            String chatId = String.valueOf(update.getMessage().getChatId());
            Integer userId = toIntExact(update.getMessage().getFrom().getId());
            String userName = update.getMessage().getFrom().getFirstName();
            String message = update.getMessage().getText();
            String email = update.getMessage().getText();

            switch (message) {
                case "/start":
                    stCommandReceived(Long.parseLong(chatId), userName);
                    break;
                case "/help":
                    sendTextHelp(Long.parseLong(chatId), help_text);
                    break;
                case "/categories":
                    spCategories(Long.parseLong(chatId));
                    break;
                case "/info":
                    Handler(Long.parseLong(chatId));
                    break;
                case "/reg":
                    sendResponse(chatId, userId, message);
                    break;
                default:
                    if (isEmailValid(message)) {
                        addUserToDatabase(id,chatId, userName, email, message);

                    } else {
                        NotSupportedSendMessage(Long.parseLong(chatId), "Команада не поддерживается");
                    }
                    break;
            }
        }
    }

    //метод регистрации
    private void sendResponse(String chatId, Integer userId, String text) {
        SendMessage message = new SendMessage();
        message.setChatId(String.valueOf(chatId));
        message.setText("Введите вашу почту для регистрации");
        executeMessageText(message);
    }

    //добавление пользователя в бд
    private void addUserToDatabase(Integer id,String chatId, String userName, String email,String text) {
            userRepository.saveMessage(new User2(id,chatId,userName,email));
            SendMessage message = new SendMessage();
            message.setChatId(String.valueOf(chatId));
            message.setText("Вы успешно зарегестрированы");
            executeMessageText(message);
        }


    // прверка валидности email
    private boolean isEmailValid(String email) {
        String emailRegex = "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$";
        return email.matches(emailRegex);
    }

    //метод команды /start
    private void stCommandReceived(long chatId, String userName) {
        SendMessage message = new SendMessage();
        message.setChatId(String.valueOf(chatId));
        message.setText("Привет," + userName + "! Добро пожаловать в наш интернет магазин!");
        executeMessageText(message);
    }

    //метод комманды /help
    private void sendTextHelp(long chatId, String text) {
        SendMessage message = new SendMessage();
        message.setChatId(String.valueOf(chatId));
        message.setText(text);
        executeMessageText(message);
    }

    // команда категорий товаров
    private void spCategories(long chatId) {
        SendMessage message = new SendMessage();
        message.setChatId(String.valueOf(chatId));
        message.setText("У нас предствлен широкий выбор компьютерной техники и расхдных материалов:\n" +
                "Мониторы,\n" +
                "Системные блоки,\n" +
                "Ноутбуки,\n" +
                "Видеокарты,\n" +
                "Процессоры,\n" +
                "Оперативная память,\n" +
                "Принтеры"
        );
        executeMessageText(message);
    }

    // метод для создания кнопок
    public void Handler(long chatId) {
        SendMessage msg = new SendMessage();
        msg.setChatId(String.valueOf(chatId));
        msg.setText("Выберите дальнейшие действия");

        InlineKeyboardMarkup markupInLine = new InlineKeyboardMarkup();
        List<List<InlineKeyboardButton>> rowsInLine = new ArrayList<>();
        List<InlineKeyboardButton> rowInLine = new ArrayList<>();
        //кнопка1
        var buttonFirst = new InlineKeyboardButton();
        buttonFirst.setText("Магазин на карте");
        buttonFirst.setCallbackData(LOK_BUTTON);
        //кнопка2
        var buttonSecond = new InlineKeyboardButton();
        buttonSecond.setText("Товары");
        buttonSecond.setCallbackData(DS_BUTTON);
        rowInLine.add(buttonFirst);
        rowInLine.add(buttonSecond);
        rowsInLine.add(rowInLine);
        markupInLine.setKeyboard(rowsInLine);
        msg.setReplyMarkup(markupInLine);
        executeMessage(msg);
    }

    // иформация о не существующей команде
    private void executeMessage(SendMessage message) {
        try {
            execute(message);
        } catch (TelegramApiException e) {
            logger.error(error_text + e.getMessage());
        }
    }

    private void NotSupportedSendMessage(long chatId, String textToSend) {
        SendMessage msg = new SendMessage();
        msg.setChatId(String.valueOf(chatId));
        msg.setText(textToSend);
        executeMessage(msg);
    }

    // ссылаемый метод для информации при нажатии кнопки
    private void executeEditMessageText(String text, long chatId, long messageId) {
        EditMessageText message = new EditMessageText();
        message.setChatId(String.valueOf(chatId));
        message.setText(text);
        message.setMessageId((int) messageId);
        //можно вынести в отдельный метод
        try {
            execute(message);
        } catch (TelegramApiException e) {
            logger.error(error_text + e.getMessage());
        }
    }

    // выносим рефакторинг в отделный метод
    private void executeMessageText(SendMessage message) {
        try {
            execute(message);
        } catch (TelegramApiException e) {
            logger.error(error_text + e.getMessage());
        }
    }
}

