package rustamscode.categorytreebot.telegram;

import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.bots.TelegramWebhookBot;
import org.telegram.telegrambots.meta.api.methods.BotApiMethod;
import org.telegram.telegrambots.meta.api.objects.Update;
import rustamscode.categorytreebot.service.UpdateDispatcher;

@Component
@FieldDefaults(level = AccessLevel.PRIVATE)
@Slf4j
public class Bot extends TelegramWebhookBot {

    final TelegramProperties telegramProperties;
    final UpdateDispatcher updateDispatcher;

    @Autowired
    public Bot(TelegramProperties telegramProperties, UpdateDispatcher updateDispatcher) {
        super(telegramProperties.getToken());
        this.telegramProperties = telegramProperties;
        this.updateDispatcher = updateDispatcher;
    }


    @Override
    public BotApiMethod<?> onWebhookUpdateReceived(Update update) {
        return updateDispatcher.distribute(update, this);

    }

    @Override
    public String getBotPath() {
        return telegramProperties.getPath();
    }

    @Override
    public String getBotUsername() {
        return telegramProperties.getUsername();
    }
}
