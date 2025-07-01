import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { BehaviorSubject, Observable, throwError } from 'rxjs';
import { tap, catchError } from 'rxjs/operators';
import { BoardGame, BoardGameDto } from '../models/board-game.model';

@Injectable({
  providedIn: 'root'
})
export class BoardGameService {
  private apiUrl = 'http://localhost:8080/api/boardgames';
  private gamesSubject = new BehaviorSubject<BoardGame[]>([]);
  public games$ = this.gamesSubject.asObservable();

  constructor(private http: HttpClient) { }

  getAllGames(): Observable<BoardGame[]> {
    return this.http.get<BoardGame[]>(this.apiUrl).pipe(
      tap((games: BoardGame[]) => {
        this.gamesSubject.next(games);
      }),
      catchError(err => {
        console.error('Error loading games:', err);
        return throwError(err);
      })
    );
  }

  createGame(gameDto: BoardGameDto): Observable<BoardGame> {
    const body = {
      ...gameDto,
      releaseDate: gameDto.releaseDate.toISOString()
    };

    return this.http.post<BoardGame>(this.apiUrl, body).pipe(
      tap((newGame: BoardGame) => {
        const updatedGames = [...this.gamesSubject.value, {
          ...newGame,
          releaseDate: new Date(newGame.releaseDate)
        }];
        this.gamesSubject.next(updatedGames);
      }),
      catchError(err => {
        console.error('Error creating game:', err);
        return throwError(err);
      })
    );
  }

  // Остальные методы сервиса...

  getGameById(id: number): Observable<BoardGame> {
    return this.http.get<BoardGame>(`${this.apiUrl}/${id}`);
  }

}