import { HttpClient, HttpErrorResponse } from '@angular/common/http';
import { inject, Service } from '@angular/core';
import { catchError, map, Observable, throwError } from 'rxjs';
import { Acteur, ActeurCreation } from './acteur.model';
import { Film } from './film.model';
import { Page } from './page.model';

@Service()
export class ActeurService {
    private http = inject(HttpClient);
    private url = '/api/acteurs';

    getAll(): Observable<Acteur[]> {
        return this.http.get<Page<Acteur>>(this.url, { params: { size: 100 } }).pipe(
            map((p) => p.content),
            catchError((e) => this.gererErreur(e)),
        );
    }

    getById(id: number): Observable<Acteur> {
        return this.http.get<Acteur>(`${this.url}/${id}`).pipe(catchError((e) => this.gererErreur(e)));
    }

    creer(a: ActeurCreation): Observable<Acteur> {
        return this.http.post<Acteur>(this.url, a).pipe(catchError((e) => this.gererErreur(e)));
    }

    modifier(id: number, a: ActeurCreation): Observable<Acteur> {
        return this.http.put<Acteur>(`${this.url}/${id}`, a).pipe(catchError((e) => this.gererErreur(e)));
    }

    supprimer(id: number): Observable<void> {
        return this.http.delete<void>(`${this.url}/${id}`).pipe(catchError((e) => this.gererErreur(e)));
    }

    getFilms(id: number): Observable<Film[]> {
        return this.http.get<Film[]>(`${this.url}/${id}/films`).pipe(catchError((e) => this.gererErreur(e)));
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
