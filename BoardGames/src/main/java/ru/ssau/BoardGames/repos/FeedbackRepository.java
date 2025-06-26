package ru.ssau.BoardGames.repos;

import ru.ssau.BoardGames.entity.Feedback;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface FeedbackRepository extends JpaRepository<Feedback, Long> {
    List<Feedback> findByUser_UserId(Long userId);
    List<Feedback> findByGame_GameId(Long gameId);
    List<Feedback> findByRating(Integer rating);
}