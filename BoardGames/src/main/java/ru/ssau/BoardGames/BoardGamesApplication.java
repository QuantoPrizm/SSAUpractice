package ru.ssau.BoardGames;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.annotation.PropertySources;

@SpringBootApplication
@PropertySources({
		@PropertySource("classpath:application.properties"),
		@PropertySource("classpath:privateApplication.properties")
})
public class BoardGamesApplication {
	public static void main(String[] args) {
		SpringApplication.run(BoardGamesApplication.class, args);
	}
}
