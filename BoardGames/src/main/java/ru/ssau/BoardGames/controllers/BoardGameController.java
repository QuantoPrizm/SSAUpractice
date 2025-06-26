package ru.ssau.BoardGames.controllers;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.ssau.BoardGames.entity.BoardGame;
import ru.ssau.BoardGames.repos.BoardGameRepository;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/boardgames")
public class BoardGameController {

    private final BoardGameRepository boardGameRepository;

    public BoardGameController(BoardGameRepository boardGameRepository) {
        this.boardGameRepository = boardGameRepository;
    }

    @GetMapping
    public List<BoardGame> getAllBoardGames() {
        return boardGameRepository.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<BoardGame> getBoardGameById(@PathVariable Long id) {
        Optional<BoardGame> game = boardGameRepository.findById(id);
        return game.map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public BoardGame createBoardGame(@RequestBody BoardGame boardGame) {
        return boardGameRepository.save(boardGame);
    }

    @PutMapping("/{id}")
    public ResponseEntity<BoardGame> updateBoardGame(@PathVariable Long id, @RequestBody BoardGame gameDetails) {
        return boardGameRepository.findById(id)
                .map(game -> {
                    game.setTitle(gameDetails.getTitle());
                    game.setPublisher(gameDetails.getPublisher());
                    game.setReleaseDate(gameDetails.getReleaseDate());
                    return ResponseEntity.ok(boardGameRepository.save(game));
                })
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteBoardGame(@PathVariable Long id) {
        if (boardGameRepository.existsById(id)) {
            boardGameRepository.deleteById(id);
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }
}