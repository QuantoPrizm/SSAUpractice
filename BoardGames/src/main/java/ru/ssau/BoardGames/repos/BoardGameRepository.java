package ru.ssau.BoardGames.repos;

import ru.ssau.BoardGames.entity.BoardGame;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BoardGameRepository extends JpaRepository<BoardGame, Long> {

}