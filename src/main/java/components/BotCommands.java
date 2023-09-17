package components;

import org.telegram.telegrambots.meta.api.objects.commands.BotCommand;

import java.util.List;

public interface BotCommands {

    List<BotCommand> commands = List.of(
            new BotCommand("/start", "Запуск бота"),
            new BotCommand("/help", "Меню помощи"),
            new BotCommand("/categories", "Категории товаров"),
            new BotCommand("/info","Кнопки для выбора действий"),
            new BotCommand("/reg","Добавление пользователя в базу после регистрации")
    );
    String help_text = new StringBuilder().append("Этот бот поможет вам выбрать интересующий вас товар из категорий\n" +
            "").append("Вам доступны следующие комманды:\n\n ").append("/start - запустить бота\n" +
            "").append("/help - меню помощи\n").append("/reg - регистрация на сайте\n" +
            "").append("/categories - информация о категориях товаров\n" +
            "").append("/info - кнопки для выбра действий").toString();

    String error_text = "Error occurred:";
    String LOK_BUTTON = "Местоположение магазина";
    String DS_BUTTON = "Кнопка информации о товаре";

}