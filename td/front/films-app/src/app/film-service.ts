import { HttpClient, HttpErrorResponse } from '@angular/common/http';
import { inject, Service } from '@angular/core';
import { catchError, map, Observable, throwError } from 'rxjs';
import { Film, FilmCreation } from './film.model';
import { Genre } from './genre.model';
import { Page } from './page.model';
import { RoleCreation } from './role.model';

@Service()
export class FilmService {
    private http = inject(HttpClient);
    private url = '/api/films';

    getAll(): Observable<Film[]> {
        return this.http.get<Page<Film>>(this.url, { params: { size: 100 } }).pipe(
            map((p) => p.content),
            catchError((e) => this.gererErreur(e)),
        );
    }

    getPage(
        page: number,
        size: number,
        sort: string,
        realisateur?: string,
        genre?: Genre | null,
    ): Observable<Page<Film>> {
        const params: Record<string, string | number> = { page, size, sort };
        if (realisateur?.trim()) {
            params['realisateur'] = realisateur.trim();
        }
        if (genre) {
            params['genre'] = genre;
        }
        return this.http
            .get<Page<Film>>(this.url, { params })
            .pipe(catchError((e) => this.gererErreur(e)));
    }

    getById(id: number): Observable<Film> {
        return this.http.get<Film>(`${this.url}/${id}`).pipe(catchError((e) => this.gererErreur(e)));
    }

    creer(f: FilmCreation): Observable<Film> {
        return this.http.post<Film>(this.url, f).pipe(catchError((e) => this.gererErreur(e)));
    }

    modifier(id: number, f: FilmCreation): Observable<Film> {
        return this.http.put<Film>(`${this.url}/${id}`, f).pipe(catchError((e) => this.gererErreur(e)));
    }

    supprimer(id: number): Observable<void> {
        return this.http.delete<void>(`${this.url}/${id}`).pipe(catchError((e) => this.gererErreur(e)));
    }

    associerActeur(filmId: number, acteurId: number, role?: RoleCreation): Observable<Film> {
        return this.http
            .post<Film>(`${this.url}/${filmId}/acteurs/${acteurId}`, role ?? null)
            .pipe(catchError((e) => this.gererErreur(e)));
    }

    dissocierActeur(filmId: number, acteurId: number): Observable<void> {
        return this.http.delete<void>(`${this.url}/${filmId}/acteurs/${acteurId}`).pipe(catchError((e) => this.gererErreur(e)));
    }

    private gererErreur(e: HttpErrorResponse): Observable<never> {
        let message: string;
        if (e.error?.detail) {
            message = e.error.detail;
        } else if (e.status === 0 || e.status >= 500) {
            message = 'Le serveur est injoignable, réessayez plus tard.';
        } else {
            message = `Erreur inattendue (code ${e.status}).`;
        }
        return throwError(() => new Error(message));
    }
}
