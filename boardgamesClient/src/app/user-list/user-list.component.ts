import { Component, OnInit } from '@angular/core';
import { UserService } from '../services/user.service';
import { User } from '../models/user.model';

@Component({
  selector: 'app-user-list',
  templateUrl: './user-list.component.html',
  styleUrls: ['./user-list.component.scss']
})
export class UserListComponent implements OnInit {
  users: User[] = [];
  isLoading = true;
  error: string | null = null;

  constructor(private userService: UserService) {}

  ngOnInit(): void {
    this.loadUsers();
  }

  loadUsers(): void {
    this.isLoading = true;
    this.error = null;
    
    this.userService.getAllUsers().subscribe({
      next: (users: User[]) => {
        this.users = users;
        this.isLoading = false;
      },
      error: (err: any) => {
        this.error = 'Failed to load users';
        this.isLoading = false;
        console.error('Error:', err);
      }
    });
  }
}