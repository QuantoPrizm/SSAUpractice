package ru.ssau.BoardGames.entity.mapper;

import org.springframework.stereotype.Component;
import ru.ssau.BoardGames.entity.BoardGame;
import ru.ssau.BoardGames.entity.dto.BoardGameDto;

@Component
public class BoardGameMapper {

    public BoardGame toEntity(BoardGameDto dto) {
        BoardGame game = new BoardGame();
        game.setTitle(dto.getTitle());
        game.setPublisher(dto.getPublisher());
        game.setReleaseDate(dto.getReleaseDate());
        return game;
    }

    public BoardGameDto toDto(BoardGame game) {
        BoardGameDto dto = new BoardGameDto();
        dto.setGameId(game.getGameId());
        dto.setTitle(game.getTitle());
        dto.setPublisher(game.getPublisher());
        dto.setReleaseDate(game.getReleaseDate());
        dto.setCreatedAt(game.getCreatedAt());
        dto.setUpdatedAt(game.getUpdatedAt());
        return dto;
    }
}