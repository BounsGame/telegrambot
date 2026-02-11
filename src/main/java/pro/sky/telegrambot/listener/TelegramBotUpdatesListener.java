package pro.sky.telegrambot.listener;

import com.pengrad.telegrambot.TelegramBot;
import com.pengrad.telegrambot.UpdatesListener;
import com.pengrad.telegrambot.model.Chat;
import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.request.SendMessage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import pro.sky.telegrambot.service.NotifyService;

import javax.annotation.PostConstruct;
import java.time.DateTimeException;
import java.time.format.DateTimeParseException;
import java.util.List;

@Service
public class TelegramBotUpdatesListener implements UpdatesListener {

    private Logger logger = LoggerFactory.getLogger(TelegramBotUpdatesListener.class);

    @Autowired
    private TelegramBot telegramBot;
    @Autowired
    private NotifyService notifyService;

    //String token = "8213540640:AAGi5qEZFMk4YPUEm92rCmA-LIIKzLJd2m0";
    //private TelegramBot bot = new TelegramBot(token);

    @PostConstruct
    public void init() {
        telegramBot.setUpdatesListener(this);
    }

    @Override
    public int process(List<Update> updates) {
        updates.forEach(update -> {
            logger.info("Processing update: {}", update);

            if (update.message().from().id() == 1658576527){
                logger.info("пишет тимур");
                Chat chat = update.message().chat();
                long chatID = chat.id();
                telegramBot.execute(new SendMessage(chatID, "так ты тимур, а не ананас"));
            }

            if(update.message().text().equals("/start")){
                logger.info("зашло в /start");
                Chat chat = update.message().chat();
                long chatID = chat.id();
                telegramBot.execute(new SendMessage(chatID,"чтобы создать новую напоминалку напиши /crateNewNotify" +
                        " а также дату и время когда надо напомнить и текст напоминалки"));
            }

            if (update.message().text().contains("/crateNewNotify")){
                logger.info("создаётся новое уведомление");
                Chat chat = update.message().chat();
                long chatID = chat.id();
                try {
                    notifyService.createNewNotify(update.message().text(),update.message().chat().username(),chatID);
                    telegramBot.execute(new SendMessage(chatID,"создано новое напоминание"));
                }catch (ArrayIndexOutOfBoundsException e){
                    telegramBot.execute(new SendMessage(chatID,"между командой, датой, временем и " +
                            "текстом должны быть пробелы"));
                }catch (DateTimeParseException e){
                    telegramBot.execute(new SendMessage(chatID,"видимо вы ошиблись в написании даты и времени," +
                            " формат написания должен быть такой дд.мм.гггг чч:мм"));
                }
            }
        });
        return UpdatesListener.CONFIRMED_UPDATES_ALL;
    }

}
