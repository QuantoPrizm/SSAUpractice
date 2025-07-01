import { Component, ViewChild } from '@angular/core';
import { CommonModule } from '@angular/common';
import { RouterOutlet } from '@angular/router';
import { UserListComponent } from './user-list/user-list.component';
import { UserFormComponent } from './user-form/user-form.component';

@Component({
  selector: 'app-root',
  standalone: true,
  imports: [
    CommonModule,
    RouterOutlet,
    UserListComponent,
    UserFormComponent
  ],
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.scss']
})
export class AppComponent {
  title = 'Board Games Client';
  selectedUserId?: number;
  showForm = false;

  @ViewChild(UserListComponent) userListComponent?: UserListComponent;

  onUserSaved(): void {
    this.showForm = false;
    this.selectedUserId = undefined;
    if (this.userListComponent) {
      this.userListComponent.loadUsers();
    }
  }

  openCreateForm(): void {
    this.selectedUserId = undefined;
    this.showForm = true;
  }

  openEditForm(userId: number): void {
    this.selectedUserId = userId;
    this.showForm = true;
  }
}