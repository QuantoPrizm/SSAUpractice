package ru.ssau.BoardGames.entity;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "feedback")
public class Feedback {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "feedback_id")
    private Long feedbackId;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @ManyToOne
    @JoinColumn(name = "game_id", nullable = false)
    private BoardGame game;

    @Column(nullable = false)
    private Integer rating;

    private String comment;

    @Column(name = "created_at")
    private LocalDateTime createdAt = LocalDateTime.now();

    // Геттеры
    public Long getFeedbackId() {
        return feedbackId;
    }

    public User getUser() {
        return user;
    }

    public BoardGame getGame() {
        return game;
    }

    public Integer getRating() {
        return rating;
    }

    public String getComment() {
        return comment;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    // Сеттеры
    public void setFeedbackId(Long feedbackId) {
        this.feedbackId = feedbackId;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public void setGame(BoardGame game) {
        this.game = game;
    }

    public void setRating(Integer rating) {
        this.rating = rating;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }
}