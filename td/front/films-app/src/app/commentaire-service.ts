import { HttpClient, HttpErrorResponse } from '@angular/common/http';
import { inject, Service } from '@angular/core';
import { catchError, Observable, throwError } from 'rxjs';
import { Commentaire, CommentaireCreation } from './commentaire.model';

@Service()
export class CommentaireService {
    private http = inject(HttpClient);
    private urlFilms = '/api/films';
    private url = '/api/commentaires';

    getByFilm(filmId: number): Observable<Commentaire[]> {
        return this.http
            .get<Commentaire[]>(`${this.urlFilms}/${filmId}/commentaires`)
            .pipe(catchError((e) => this.gererErreur(e)));
    }

    creer(filmId: number, c: CommentaireCreation): Observable<Commentaire> {
        return this.http
            .post<Commentaire>(`${this.urlFilms}/${filmId}/commentaires`, c)
            .pipe(catchError((e) => this.gererErreur(e)));
    }

    modifier(id: number, c: CommentaireCreation): Observable<Commentaire> {
        return this.http
            .put<Commentaire>(`${this.url}/${id}`, c)
            .pipe(catchError((e) => this.gererErreur(e)));
    }

    supprimer(id: number): Observable<void> {
        return this.http
            .delete<void>(`${this.url}/${id}`)
            .pipe(catchError((e) => this.gererErreur(e)));
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
