package ru.ssau.BoardGames.entity.mapper;

import org.springframework.stereotype.Component;
import ru.ssau.BoardGames.entity.Feedback;
import ru.ssau.BoardGames.entity.dto.FeedbackDto;
import ru.ssau.BoardGames.entity.User;
import ru.ssau.BoardGames.entity.BoardGame;

@Component
public class FeedBackMapper {

    public Feedback toEntity(FeedbackDto dto, User user, BoardGame game) {
        Feedback feedback = new Feedback();
        feedback.setUser(user);
        feedback.setGame(game);
        feedback.setRating(dto.getRating());
        feedback.setComment(dto.getComment());
        return feedback;
    }

    public FeedbackDto toDto(Feedback feedback) {
        FeedbackDto dto = new FeedbackDto();
        dto.setFeedbackId(feedback.getFeedbackId());
        dto.setUserId(feedback.getUser().getUserId());
        dto.setGameId(feedback.getGame().getGameId());
        dto.setRating(feedback.getRating());
        dto.setComment(feedback.getComment());
        dto.setCreatedAt(feedback.getCreatedAt());
        return dto;
    }
}