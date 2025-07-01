export interface Feedback {
  feedbackId?: number;
  userId: number;
  gameId: number;
  rating: number;
  comment: string;
  createdAt?: Date;
  userName?: string;
}