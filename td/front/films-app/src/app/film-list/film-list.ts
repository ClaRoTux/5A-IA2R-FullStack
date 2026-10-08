import { Component, inject, signal } from '@angular/core';
import { DatePipe } from '@angular/common';
import { RouterLink } from '@angular/router';
import { toSignal } from '@angular/core/rxjs-interop';
import { catchError, of } from 'rxjs';
import { FilmService } from '../film-service';
import { Film } from '../film.model';

@Component({
  imports: [RouterLink, DatePipe],
  selector: 'app-film-list',
  styleUrl: './film-list.css',
  templateUrl: './film-list.html',
})
export class FilmList {
  private service = inject(FilmService);
  erreur = signal<string | null>(null);
  films = toSignal(
    this.service.getAll().pipe(
      catchError((e: Error) => {
        this.erreur.set(e.message);
        return of([]);
      }),
    ),
    { initialValue: [] },
  );

  avant2000(f: Film): boolean {
    return new Date(f.dateSortie).getFullYear() < 2000;
  }
}
