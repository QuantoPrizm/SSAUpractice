package ru.ssau.BoardGames.repos;

import ru.ssau.BoardGames.entity.BoardGame;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface BoardGameRepository extends JpaRepository<BoardGame, Long> {
    List<BoardGame> findByTitleContainingIgnoreCase(String title);
    List<BoardGame> findByPublisher(String publisher);
}