import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { Feedback } from '../models/feedback.model';

@Injectable({
  providedIn: 'root'
})
export class FeedbackService {
  private apiUrl = 'http://localhost:8080/api/feedback';

  constructor(private http: HttpClient) { }

  getGameFeedback(gameId: number): Observable<Feedback[]> {
    return this.http.get<Feedback[]>(`${this.apiUrl}/game/${gameId}`);
  }

  createFeedback(feedback: Feedback): Observable<Feedback> {
    return this.http.post<Feedback>(this.apiUrl, feedback);
  }
}