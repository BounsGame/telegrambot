package pro.sky.telegrambot.service;

import com.pengrad.telegrambot.TelegramBot;
import com.pengrad.telegrambot.request.SendMessage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import pro.sky.telegrambot.listener.TelegramBotUpdatesListener;
import pro.sky.telegrambot.model.ChatTable;
import pro.sky.telegrambot.repository.ChatRepository;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.List;

@Service
public class NotifyService {

    @Autowired
    private TelegramBot telegramBot;

    private Logger logger = LoggerFactory.getLogger(NotifyService.class);

    private ChatRepository chatRepository;

    public NotifyService(ChatRepository chatRepository) {
        this.chatRepository = chatRepository;
    }

    public void createNewNotify(String text,String name,long chatID){
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

        ChatTable newNotify = new ChatTable();
        newNotify.setText(subText);
        newNotify.setTime(dateTime);
        newNotify.setChatID(chatID);
        newNotify.setName(name);
        chatRepository.save(newNotify);
    }

    @Scheduled(fixedRate = 20000)
    public void checkNotify(){
        LocalDateTime start = LocalDateTime.now().truncatedTo(ChronoUnit.MINUTES);
        LocalDateTime end = LocalDateTime.now().truncatedTo(ChronoUnit.MINUTES).plusMinutes(1);
        List<ChatTable> chat = chatRepository.findBetweenNotifyTime(start,end);
        if (!chat.isEmpty()) {
            String eventMessage = chat.toString();
            telegramBot.execute(new SendMessage(chat.get(0).getChatID(), eventMessage));
            for (ChatTable chatTable : chat) {
                chatRepository.delete(chatTable);
            }
        }
    }
}
