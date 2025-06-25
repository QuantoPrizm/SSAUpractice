package ru.ssau.BoardGames.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "boardgames")
public class BoardGame {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String title;

    private String publisher;
    private int minPlayers;
    private int maxPlayers;
    private int playtime;

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
}