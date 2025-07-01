import { Component, EventEmitter, Input, OnInit, Output } from '@angular/core';
import { UserService } from '../services/user.service';
import { UserDto } from '../models/user.model';
import { FormsModule } from '@angular/forms';
import { CommonModule } from '@angular/common';

@Component({
  selector: 'app-user-form',
  standalone: true,
  imports: [FormsModule, CommonModule],
  templateUrl: './user-form.component.html',
  styleUrls: ['./user-form.component.scss']
})
export class UserFormComponent implements OnInit {
  @Input() userId?: number;
  @Output() userSaved = new EventEmitter<void>();

  userDto: UserDto = {
    email: '',
    password: ''
  };

  constructor(private userService: UserService) { }

  ngOnInit(): void {
    if (this.userId) {
      this.userService.getUserById(this.userId).subscribe(user => {
        this.userDto = {
          email: user.email,
          password: ''
        };
      });
    }
  }

  saveUser(): void {
    if (this.userId) {
      this.userService.updateUser(this.userId, this.userDto).subscribe(
        () => this.userSaved.emit(),
        error => console.error('Error updating user', error)
      );
    } else {
      this.userService.createUser(this.userDto).subscribe(
        () => this.userSaved.emit(),
        error => console.error('Error creating user', error)
      );
    }
  }
}