package ru.ssau.BoardGames.controllers;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.ssau.BoardGames.entity.Feedback;
import ru.ssau.BoardGames.repos.FeedbackRepository;
import ru.ssau.BoardGames.repos.BoardGameRepository;
import ru.ssau.BoardGames.repos.UserRepository;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/feedback")
public class FeedbackController {

    private final FeedbackRepository feedbackRepository;
    private final UserRepository userRepository;
    private final BoardGameRepository boardGameRepository;

    public FeedbackController(FeedbackRepository feedbackRepository,
                              UserRepository userRepository,
                              BoardGameRepository boardGameRepository) {
        this.feedbackRepository = feedbackRepository;
        this.userRepository = userRepository;
        this.boardGameRepository = boardGameRepository;
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
    public ResponseEntity<Feedback> createFeedback(@RequestBody Feedback feedback) {
        if (!userRepository.existsById(feedback.getUser().getUserId()) ||
                !boardGameRepository.existsById(feedback.getGame().getGameId())) {
            return ResponseEntity.badRequest().build();
        }
        return ResponseEntity.ok(feedbackRepository.save(feedback));
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