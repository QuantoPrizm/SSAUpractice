import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable, throwError } from 'rxjs';
import { catchError } from 'rxjs/operators';
import { User, UserDto } from '../models/user.model';

@Injectable({
  providedIn: 'root'
})
export class UserService {
  private apiUrl = 'http://localhost:8080/api/users';

  constructor(private http: HttpClient) { }

  // Получение всех пользователей
  getAllUsers(): Observable<User[]> {
    return this.http.get<User[]>(this.apiUrl).pipe(
      catchError(this.handleError)
    );
  }

  // Создание нового пользователя
  createUser(userDto: UserDto): Observable<User> {
    return this.http.post<User>(this.apiUrl, userDto).pipe(
      catchError(this.handleError)
    );
  }

  // Обновление пользователя
  updateUser(id: number, userDto: UserDto): Observable<User> {
    return this.http.put<User>(`${this.apiUrl}/${id}`, userDto).pipe(
      catchError(this.handleError)
    );
  }
  
  getUserById(id: number): Observable<User> {
  return this.http.get<User>(`${this.apiUrl}/${id}`).pipe(
    catchError(this.handleError)
  );
}

  // Базовая обработка ошибок
  private handleError(error: any): Observable<never> {
    console.error('An error occurred:', error);
    return throwError(() => new Error('Something went wrong'));
  }
}