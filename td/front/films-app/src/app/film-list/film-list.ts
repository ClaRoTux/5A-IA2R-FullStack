import { Component, inject, signal } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { RouterLink } from '@angular/router';
import { rxResource } from '@angular/core/rxjs-interop';
import { FilmService } from '../film-service';
import { Film } from '../film.model';
import { Genre, GENRES } from '../genre.model';
import { FilmCard } from '../film-card/film-card';
import { Pagination } from '../pagination/pagination';

@Component({
  imports: [RouterLink, FormsModule, FilmCard, Pagination],
  selector: 'app-film-list',
  styleUrl: './film-list.css',
  templateUrl: './film-list.html',
})
export class FilmList {
  private service = inject(FilmService);
  private taille = 5;
  genres = GENRES;

  page = signal(0);
  tri = signal('titre,asc');
  realisateur = signal('');
  genre = signal<Genre | null>(null);
  erreurAction = signal<string | null>(null);

  films = rxResource({
    params: () => ({
      page: this.page(),
      tri: this.tri(),
      realisateur: this.realisateur(),
      genre: this.genre(),
    }),
    stream: ({ params }) =>
      this.service.getPage(params.page, this.taille, params.tri, params.realisateur, params.genre),
  });

  changerTri(tri: string) {
    this.tri.set(tri);
    this.page.set(0);
  }

  changerRealisateur(realisateur: string) {
    this.realisateur.set(realisateur);
    this.page.set(0);
  }

  changerGenre(genre: Genre | null) {
    this.genre.set(genre);
    this.page.set(0);
  }

  onSupprimer(f: Film) {
    if (!confirm(`Supprimer « ${f.titre} » ?`)) {
      return;
    }
    this.erreurAction.set(null);
    this.service.supprimer(f.id).subscribe({
      next: () => {
        const dernierDeLaPage = this.films.value()?.content.length === 1;
        if (dernierDeLaPage && this.page() > 0) {
          this.page.set(this.page() - 1);
        } else {
          this.films.reload();
        }
      },
      error: (e: Error) => this.erreurAction.set(e.message),
    });
  }
}
