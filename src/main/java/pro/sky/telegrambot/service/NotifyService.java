package pro.sky.telegrambot.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pro.sky.telegrambot.listener.TelegramBotUpdatesListener;
import pro.sky.telegrambot.repository.ChatRepository;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Service
public class NotifyService {

    private Logger logger = LoggerFactory.getLogger(NotifyService.class);

    private ChatRepository chatRepository;

    public NotifyService(ChatRepository chatRepository) {
        this.chatRepository = chatRepository;
    }

    public void createNewNotify(String text){
        logger.info("зашол в метод создания уведомления");
        String[] parts = text.split(" ",4);

        String command = parts[0];
        String date = parts[1];
        String time = parts[2];
        String subText = parts[3];
        logger.info("com " + command + " date " + date + " time " + time + "sub " + subText);
        String con = date + " " + time;
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm");
        LocalDateTime dateTime = LocalDateTime.parse(con,formatter);
        logger.info("dateTime " + dateTime);
    }
}
