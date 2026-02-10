package pro.sky.telegrambot.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import pro.sky.telegrambot.model.ChatTable;

import java.util.List;

@Repository
public interface ChatRepository extends JpaRepository<ChatTable, Long> {

    @Query(value = "SELECT * FROM chat", nativeQuery = true)
    List<ChatTable> findAll();



}
