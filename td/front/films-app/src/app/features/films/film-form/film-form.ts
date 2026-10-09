import { Component, computed, effect, inject, input, signal } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { Router, RouterLink } from '@angular/router';
import { rxResource } from '@angular/core/rxjs-interop';
import { FilmService } from '../../../services/film-service';
import { FilmCreation } from '../../../models/film.model';
import { Genre, GENRES } from '../../../models/genre.model';

@Component({
  imports: [FormsModule, RouterLink],
  selector: 'app-film-form',
  styleUrl: './film-form.css',
  templateUrl: './film-form.html',
})
export class FilmForm {
  private service = inject(FilmService);
  private router = inject(Router);

  id = input<string>();
  filmId = computed(() => (this.id() ? Number(this.id()) : undefined));
  edition = computed(() => this.filmId() !== undefined);
  genres = GENRES;

  titre = signal('');
  realisateur = signal('');
  dateSortie = signal('');
  genre = signal<Genre | null>(null);
  erreur = signal<string | null>(null);

  film = rxResource({
    params: () => this.filmId(),
    stream: ({ params }) => this.service.getById(params),
  });

  constructor() {
    effect(() => {
      if (!this.film.hasValue()) {
        return;
      }
      const f = this.film.value();
      this.titre.set(f.titre);
      this.realisateur.set(f.realisateur);
      this.dateSortie.set(f.dateSortie);
      this.genre.set(f.genre);
    });
  }

  enregistrer() {
    const genre = this.genre();
    if (!genre) {
      return;
    }
    const data: FilmCreation = {
      titre: this.titre(),
      realisateur: this.realisateur(),
      dateSortie: this.dateSortie(),
      genre,
    };
    const id = this.filmId();
    const requete = id === undefined ? this.service.creer(data) : this.service.modifier(id, data);
    this.erreur.set(null);
    requete.subscribe({
      next: (f) => this.router.navigate(['/films', f.id]),
      error: (e: Error) => this.erreur.set(e.message),
    });
  }
}
