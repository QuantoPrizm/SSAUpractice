import { Component, OnInit } from '@angular/core';
import { CommonModule, DatePipe, DecimalPipe } from '@angular/common';
import { RouterModule } from '@angular/router';
import { BoardGameService } from '../services/board-game.service';
import { BoardGame } from '../models/board-game.model';

@Component({
  selector: 'app-game-list',
  standalone: true,
  imports: [
    CommonModule,
    RouterModule,
    DatePipe,
    DecimalPipe
  ],
  templateUrl: './game-list.component.html',
  styleUrls: ['./game-list.component.scss']
})
export class GameListComponent implements OnInit {
  games: BoardGame[] = [];
  originalGames: BoardGame[] = []; // Для хранения оригинального порядка
  currentSort: 'default' | 'rating-asc' | 'rating-desc' = 'default';
  
  constructor(private gameService: BoardGameService) {}

  ngOnInit(): void {
    this.loadGames();
  }

  private loadGames(): void {
    this.gameService.getAllGames().subscribe({
      next: (games) => {
        this.games = games.map(game => ({
          ...game,
          gameId: Number(game.gameId),
          releaseDate: new Date(game.releaseDate)
        }));
        this.originalGames = [...this.games]; // Сохраняем оригинальный порядок
        console.log('Games loaded:', this.games);
      },
      error: (err) => console.error('Failed to load games:', err)
    });
  }

  // Метод для сортировки игр
  sortGames(sortType: 'default' | 'rating-asc' | 'rating-desc'): void {
    this.currentSort = sortType;
    
    switch(sortType) {
      case 'rating-desc':
        this.games.sort((a, b) => (b.averageRating || 0) - (a.averageRating || 0));
        break;
      case 'rating-asc':
        this.games.sort((a, b) => (a.averageRating || 0) - (b.averageRating || 0));
        break;
      default:
        this.games = [...this.originalGames]; // Восстанавливаем оригинальный порядок
    }
  }
}