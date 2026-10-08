import { Component, inject, signal } from '@angular/core';
import { RouterLink } from '@angular/router';
import { rxResource } from '@angular/core/rxjs-interop';
import { FilmService } from '../film-service';
import { Film } from '../film.model';
import { FilmCard } from '../film-card/film-card';

@Component({
  imports: [RouterLink, FilmCard],
  selector: 'app-film-list',
  styleUrl: './film-list.css',
  templateUrl: './film-list.html',
})
export class FilmList {
  private service = inject(FilmService);
  erreurAction = signal<string | null>(null);

  films = rxResource({
    stream: () => this.service.getAll(),
  });

  onSupprimer(f: Film) {
    if (!confirm(`Supprimer « ${f.titre} » ?`)) {
      return;
    }
    this.erreurAction.set(null);
    this.service.supprimer(f.id).subscribe({
      next: () => this.films.reload(),
      error: (e: Error) => this.erreurAction.set(e.message),
    });
  }
}
