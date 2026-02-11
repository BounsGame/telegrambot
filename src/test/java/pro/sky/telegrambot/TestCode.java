package pro.sky.telegrambot;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import pro.sky.telegrambot.service.NotifyService;

@SpringBootTest
public class TestCode {

    @Autowired
    NotifyService notifyService;

    @Test
    public void test(){
        String text = "/crateNewNotify 01.12.2004 10:00 что то";


    }
}
