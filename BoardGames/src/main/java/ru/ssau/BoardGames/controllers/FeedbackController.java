package ru.ssau.BoardGames.controllers;

import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import ru.ssau.BoardGames.entity.BoardGame;
import ru.ssau.BoardGames.entity.Feedback;
import ru.ssau.BoardGames.entity.User;
import ru.ssau.BoardGames.entity.dto.FeedbackDto;
import ru.ssau.BoardGames.entity.mapper.FeedBackMapper;
import ru.ssau.BoardGames.repos.FeedbackRepository;
import ru.ssau.BoardGames.repos.BoardGameRepository;
import ru.ssau.BoardGames.repos.UserRepository;

import java.security.Principal;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/feedback")
public class FeedbackController {

    private final FeedbackRepository feedbackRepository;
    private final UserRepository userRepository;
    private final BoardGameRepository boardGameRepository;
    private final FeedBackMapper feedbackMapper;

    public FeedbackController(FeedbackRepository feedbackRepository,
                              UserRepository userRepository,
                              BoardGameRepository boardGameRepository,
                              FeedBackMapper feedbackMapper) {
        this.feedbackRepository = feedbackRepository;
        this.userRepository = userRepository;
        this.boardGameRepository = boardGameRepository;
        this.feedbackMapper = feedbackMapper;
    }

    @GetMapping
    public List<Feedback> getAllFeedback() {
        return feedbackRepository.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Feedback> getFeedbackById(@PathVariable Long id) {
        Optional<Feedback> feedback = feedbackRepository.findById(id);
        return feedback.map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<FeedbackDto> createFeedback(Principal principal,
                                                      @Valid @RequestBody FeedbackDto feedbackDto,
                                                      BindingResult result) {

        if (result.hasErrors()) {
            return ResponseEntity.badRequest().build();
        }

        // Получаем пользователя и игру из БД
        User user = userRepository.findById(feedbackDto.getUserId())
                .orElseThrow(() -> new RuntimeException("User not found"));
        BoardGame game = boardGameRepository.findById(feedbackDto.getGameId())
                .orElseThrow(() -> new RuntimeException("Game not found"));

        // Преобразование DTO в сущность и сохранение
        Feedback feedback = feedbackMapper.toEntity(feedbackDto, user, game);
        Feedback savedFeedback = feedbackRepository.save(feedback);

        // Обновление среднего рейтинга игры
        updateGameRating(game.getGameId());

        return ResponseEntity.ok(feedbackMapper.toDto(savedFeedback));
    }

    private void updateGameRating(Long gameId) {
        Double avgRating = feedbackRepository.calculateAverageRatingByGameId(gameId);
        boardGameRepository.findById(gameId).ifPresent(game -> {
            game.setAverageRating(avgRating != null ? avgRating : 0.0);
            boardGameRepository.save(game);
        });
    }

    @PutMapping("/{id}")
    public ResponseEntity<Feedback> updateFeedback(@PathVariable Long id, @RequestBody Feedback feedbackDetails) {
        return feedbackRepository.findById(id)
                .map(feedback -> {
                    feedback.setRating(feedbackDetails.getRating());
                    feedback.setComment(feedbackDetails.getComment());
                    return ResponseEntity.ok(feedbackRepository.save(feedback));
                })
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteFeedback(@PathVariable Long id) {
        if (feedbackRepository.existsById(id)) {
            feedbackRepository.deleteById(id);
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }

    @GetMapping("/game/{gameId}")
    public List<Feedback> getFeedbackByGame(@PathVariable Long gameId) {
        return feedbackRepository.findByGame_GameId(gameId);
    }

    @GetMapping("/user/{userId}")
    public List<Feedback> getFeedbackByUser(@PathVariable Long userId) {
        return feedbackRepository.findByUser_UserId(userId);
    }
}