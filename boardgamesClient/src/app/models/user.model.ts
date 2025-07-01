export interface User {
  userId: number;
  email: string;
  passwordHash?: string;
  createdAt?: Date;
  lastLogin?: Date;
}

export interface UserDto {
  email: string;
  password?: string;
}