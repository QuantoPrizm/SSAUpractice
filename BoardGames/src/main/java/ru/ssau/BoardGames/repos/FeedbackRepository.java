package ru.ssau.BoardGames.repos;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import ru.ssau.BoardGames.entity.Feedback;
import java.util.List;

public interface FeedbackRepository extends JpaRepository<Feedback, Long> {
    List<Feedback> findByUser_UserId(Long userId);
    List<Feedback> findByGame_GameId(Long gameId);
    List<Feedback> findByRating(Integer rating);

    @Query("SELECT AVG(f.rating) FROM Feedback f WHERE f.game.gameId = :gameId")
    Double calculateAverageRatingByGameId(Long gameId);
}