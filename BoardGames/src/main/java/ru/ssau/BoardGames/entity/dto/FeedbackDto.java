package ru.ssau.BoardGames.entity.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class FeedbackDto {
    private Long feedbackId;
    private Long userId;
    private Long gameId;
    private Integer rating;
    private String comment;
    private String userName; // Для отображения имени пользователя
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime createdAt;
}