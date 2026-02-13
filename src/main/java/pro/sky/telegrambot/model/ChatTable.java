package pro.sky.telegrambot.model;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.time.ZonedDateTime;

@Entity
@Table(name = "Chat")
public class ChatTable {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;

    @Column(name = "chatID")
    private Long chatID;

    @Column(name = "NotifyText")
    private String text;

    @Column(name = "NotifyTime")
    private LocalDateTime time;

    @Column(name = "UserName")
    private String name;

    public ChatTable() {}

    public Long getChatID() {
        return chatID;
    }

    public Long getId() {
        return id;
    }

    public String getText() {
        return text;
    }

    public LocalDateTime getTime() {
        return time;
    }

    public String getName() {
        return name;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setChatID(Long chatID) {
        this.chatID = chatID;
    }

    public void setText(String text) {
        this.text = text;
    }

    public void setTime(LocalDateTime time) {
        this.time = time;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString(){
        return "вы просили напомнить " + getTime() + " " + getText();
    }
}
