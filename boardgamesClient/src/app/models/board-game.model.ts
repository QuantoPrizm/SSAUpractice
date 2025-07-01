export interface BoardGame {
  gameId?: number;  // или string, если ID очень большой
  title: string;
  publisher: string;
  releaseDate: Date | string;
  averageRating?: number | null;  // явно разрешаем null
}
export interface BoardGameDto {
  title: string;
  publisher: string;
  releaseDate: Date;
}