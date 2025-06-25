package ru.ssau.BoardGames.controllers;

import ru.ssau.BoardGames.entity.BoardGame;
import ru.ssau.BoardGames.repos.BoardGameRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/games")
public class BoardGameController {

    private final BoardGameRepository repository;

    public BoardGameController(BoardGameRepository repository) {
        this.repository = repository;
    }

    // GET Все игры
    @GetMapping
    public ResponseEntity<List<BoardGame>> getAllGames() {
        List<BoardGame> games = repository.findAll();
        return new ResponseEntity<>(games, HttpStatus.OK);
    }

    // GET Игра по ID
    @GetMapping("/{id}")
    public ResponseEntity<BoardGame> getGameById(@PathVariable Long id) {
        BoardGame game = repository.findById(id).orElse(null);
        if (game != null) {
            return new ResponseEntity<>(game, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    // POST Создание новой игры
    @PostMapping
    public ResponseEntity<BoardGame> createGame(@RequestBody BoardGame game) {
        BoardGame savedGame = repository.save(game);
        return new ResponseEntity<>(savedGame, HttpStatus.CREATED);
    }

    // PUT Обновление игры
    @PutMapping("/{id}")
    public ResponseEntity<BoardGame> updateGame(
            @PathVariable Long id,
            @RequestBody BoardGame updatedGame) {

        if (repository.existsById(id)) {
            updatedGame.setId(id); // Убедимся, что ID сохраняется
            BoardGame savedGame = repository.save(updatedGame);
            return new ResponseEntity<>(savedGame, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    // DELETE Удаление игры
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteGame(@PathVariable Long id) {
        if (repository.existsById(id)) {
            repository.deleteById(id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
}