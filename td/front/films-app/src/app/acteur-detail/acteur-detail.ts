import { Component, computed, inject, input, signal } from '@angular/core';
import { DatePipe, UpperCasePipe } from '@angular/common';
import { Router, RouterLink } from '@angular/router';
import { rxResource } from '@angular/core/rxjs-interop';
import { ActeurService } from '../acteur-service';

@Component({
  imports: [RouterLink, UpperCasePipe, DatePipe],
  selector: 'app-acteur-detail',
  styleUrl: './acteur-detail.css',
  templateUrl: './acteur-detail.html',
})
export class ActeurDetail {
  private service = inject(ActeurService);
  private router = inject(Router);

  id = input.required<string>();
  acteurId = computed(() => Number(this.id()));
  erreurAction = signal<string | null>(null);

  acteur = rxResource({
    params: () => this.acteurId(),
    stream: ({ params }) => this.service.getById(params),
  });

  films = rxResource({
    params: () => this.acteurId(),
    stream: ({ params }) => this.service.getFilms(params),
  });

  supprimer() {
    if (!confirm('Supprimer cet acteur ?')) {
      return;
    }
    this.service.supprimer(this.acteurId()).subscribe({
      next: () => this.router.navigate(['/acteurs']),
      error: (e: Error) => this.erreurAction.set(e.message),
    });
  }
}
