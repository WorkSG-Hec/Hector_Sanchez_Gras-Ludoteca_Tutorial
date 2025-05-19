import { Injectable } from '@angular/core';
import { catchError, Observable, of, tap } from 'rxjs';
import { Game } from './model/Game';
import { HttpClient } from '@angular/common/http';

@Injectable({
  providedIn: 'root'
})
export class GameService {
  constructor(
    private http: HttpClient
  ) { }

  private baseUrl = 'http://localhost:8080/game';

  getGames(title?: string, categoryId?: number): Observable<Game[]> {
    const url = this.composeFindUrl(title, categoryId);

    return this.http.get<Game[]>(url).pipe(
      tap(games => {
        localStorage.setItem('games', JSON.stringify(games));
      }),
      catchError(error => {
        console.error("Error al obtener los juegos desde la API. Cargando desde localStorage.", error);
        const games = localStorage.getItem('games');
        if (games) {
          return of(JSON.parse(games));
        } else {
          return of([]);
        }
      })
    );

    // return this.http.get<Game[]>(this.composeFindUrl(title, categoryId));
  }

  saveGame(game: Game): Observable<void> {
    const { id } = game;
    const url = id ? `${this.baseUrl}/${id}` : this.baseUrl;

    return this.http.put<void>(url, game);
  }

  private composeFindUrl(title?: string, categoryId?: number): string {
    const params = new URLSearchParams();
    if (title) {
      params.set('title', title);
    }
    if (categoryId) {
      params.set('idCategory', categoryId.toString());
    }

    const queryString = params.toString();
    return queryString ? `${this.baseUrl}?${queryString}` : this.baseUrl;
  }
}
