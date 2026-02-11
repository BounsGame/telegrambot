package pro.sky.telegrambot.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import pro.sky.telegrambot.model.ChatTable;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface ChatRepository extends JpaRepository<ChatTable, Long> {

    @Query(value = "SELECT * FROM chat", nativeQuery = true)
    List<ChatTable> findAll();

    @Query(value = "SELECT * FROM Chat WHERE notify_time BETWEEN ?1 AND ?2",nativeQuery = true)
    List<ChatTable> findBetweenNotifyTime(LocalDateTime start, LocalDateTime end);



}
