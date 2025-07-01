import { Component, OnInit } from '@angular/core';
import { CommonModule, DatePipe, DecimalPipe } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { RouterModule, Router } from '@angular/router';
import { ActivatedRoute } from '@angular/router';
import { BoardGameService } from '../services/board-game.service';
import { FeedbackService } from '../services/feedback.service';
import { BoardGame } from '../models/board-game.model';
import { Feedback } from '../models/feedback.model';

@Component({
  selector: 'app-game-details',
  standalone: true,
  imports: [
    CommonModule,
    FormsModule,
    RouterModule,
    DatePipe,
    DecimalPipe
  ],
  templateUrl: './game-details.component.html',
  styleUrls: ['./game-details.component.scss']
})

export class GameDetailsComponent implements OnInit {
  game: BoardGame | null = null;
  feedbacks: Feedback[] = [];
  newFeedback: Partial<Feedback> = { rating: 5, comment: '' };

  constructor(
    private route: ActivatedRoute,
    private gameService: BoardGameService,
    private feedbackService: FeedbackService,
	private router: Router
  ) {}

goBack(): void {
  this.router.navigate(['/games']); // Четкий переход к списку игр
}

  ngOnInit(): void {
    const gameId = Number(this.route.snapshot.params['id']);
    this.loadGame(gameId);
    this.loadFeedbacks(gameId);
  }

  private loadGame(gameId: number): void {
    this.gameService.getGameById(gameId).subscribe(game => {
      this.game = game;
    });
  }

  private loadFeedbacks(gameId: number): void {
    this.feedbackService.getGameFeedback(gameId).subscribe(feedbacks => {
      this.feedbacks = feedbacks;
    });
  }

  submitFeedback(): void {
    if (this.game && this.newFeedback.rating && this.newFeedback.comment) {
      const feedback: Feedback = {
        gameId: this.game.gameId!,
        userId: 1, // TODO: Заменить на ID текущего пользователя
        rating: this.newFeedback.rating,
        comment: this.newFeedback.comment
      };
      
      this.feedbackService.createFeedback(feedback).subscribe(() => {
        this.loadFeedbacks(this.game!.gameId!);
        this.newFeedback = { rating: 5, comment: '' };
      });
    }
  }
}