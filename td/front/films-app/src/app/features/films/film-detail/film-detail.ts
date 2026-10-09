import { Component, computed, inject, input, signal } from '@angular/core';
import { DatePipe } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { Router, RouterLink } from '@angular/router';
import { rxResource } from '@angular/core/rxjs-interop';
import { FilmService } from '../../../services/film-service';
import { ActeurService } from '../../../services/acteur-service';
import { Role, RoleCreation } from '../../../models/role.model';
import { FilmCommentaires } from '../film-commentaires/film-commentaires';

@Component({
  imports: [RouterLink, DatePipe, FormsModule, FilmCommentaires],
  selector: 'app-film-detail',
  styleUrl: './film-detail.css',
  templateUrl: './film-detail.html',
})
export class FilmDetail {
  private service = inject(FilmService);
  private acteurService = inject(ActeurService);
  private router = inject(Router);

  id = input.required<string>();
  filmId = computed(() => Number(this.id()));
  erreurAction = signal<string | null>(null);
  acteurSelectionne = signal<number | null>(null);
  personnage = signal('');
  roleEnEdition = signal<number | null>(null);
  nouveauPersonnage = signal('');

  film = rxResource({
    params: () => this.filmId(),
    stream: ({ params }) => this.service.getById(params),
  });

  acteurs = rxResource({
    stream: () => this.acteurService.getAll(),
  });

  disponibles = computed(() => {
    if (!this.film.hasValue() || !this.acteurs.hasValue()) {
      return [];
    }
    const associes = new Set(this.film.value().acteurs?.map((r) => r.acteurId));
    return this.acteurs.value().filter((a) => !associes.has(a.id));
  });

  associer() {
    const acteurId = this.acteurSelectionne();
    if (!acteurId) {
      return;
    }
    const personnage = this.personnage().trim();
    this.lier(acteurId, personnage ? { personnage } : undefined);
  }

  editerRole(r: Role) {
    this.roleEnEdition.set(r.acteurId);
    this.nouveauPersonnage.set(r.personnage ?? '');
  }

  enregistrerRole(acteurId: number) {
    this.lier(acteurId, { personnage: this.nouveauPersonnage().trim() });
  }

  dissocier(acteurId: number) {
    this.erreurAction.set(null);
    this.service.dissocierActeur(this.filmId(), acteurId).subscribe({
      next: () => this.film.reload(),
      error: (e: Error) => this.erreurAction.set(e.message),
    });
  }

  supprimer() {
    if (!confirm('Supprimer ce film ?')) {
      return;
    }
    this.service.supprimer(this.filmId()).subscribe({
      next: () => this.router.navigate(['/films']),
      error: (e: Error) => this.erreurAction.set(e.message),
    });
  }

  private lier(acteurId: number, role?: RoleCreation) {
    this.erreurAction.set(null);
    this.service.associerActeur(this.filmId(), acteurId, role).subscribe({
      next: () => {
        this.acteurSelectionne.set(null);
        this.personnage.set('');
        this.roleEnEdition.set(null);
        this.film.reload();
      },
      error: (e: Error) => this.erreurAction.set(e.message),
    });
  }
}
