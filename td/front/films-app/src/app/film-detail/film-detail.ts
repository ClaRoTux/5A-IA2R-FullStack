import { Component, computed, inject, input, signal } from '@angular/core';
import { DatePipe } from '@angular/common';
import { Router, RouterLink } from '@angular/router';
import { rxResource } from '@angular/core/rxjs-interop';
import { FilmService } from '../film-service';

@Component({
  imports: [RouterLink, DatePipe],
  selector: 'app-film-detail',
  styleUrl: './film-detail.css',
  templateUrl: './film-detail.html',
})
export class FilmDetail {
  private service = inject(FilmService);
  private router = inject(Router);

  id = input.required<string>();
  filmId = computed(() => Number(this.id()));
  erreurAction = signal<string | null>(null);

  film = rxResource({
    params: () => this.filmId(),
    stream: ({ params }) => this.service.getById(params),
  });

  supprimer() {
    if (!confirm('Supprimer ce film ?')) {
      return;
    }
    this.service.supprimer(this.filmId()).subscribe({
      next: () => this.router.navigate(['/films']),
      error: (e: Error) => this.erreurAction.set(e.message),
    });
  }
}
