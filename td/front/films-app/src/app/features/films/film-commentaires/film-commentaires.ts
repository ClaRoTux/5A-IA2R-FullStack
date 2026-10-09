import { Component, inject, input, signal } from '@angular/core';
import { DatePipe } from '@angular/common';
import { FormsModule, NgForm } from '@angular/forms';
import { rxResource } from '@angular/core/rxjs-interop';
import { CommentaireService } from '../../../services/commentaire-service';
import { Commentaire } from '../../../models/commentaire.model';

@Component({
  imports: [DatePipe, FormsModule],
  selector: 'app-film-commentaires',
  styleUrl: './film-commentaires.css',
  templateUrl: './film-commentaires.html',
})
export class FilmCommentaires {
  private service = inject(CommentaireService);
  filmId = input.required<number>();

  commentaires = rxResource({
    params: () => this.filmId(),
    stream: ({ params }) => this.service.getByFilm(params),
  });

  auteur = signal('');
  message = signal('');
  erreur = signal<string | null>(null);

  ajouter(form: NgForm) {
    this.erreur.set(null);
    this.service
      .creer(this.filmId(), { auteur: this.auteur(), message: this.message() })
      .subscribe({
        next: () => {
          form.resetForm({ auteur: this.auteur(), message: '' });
          this.commentaires.reload();
        },
        error: (e: Error) => this.erreur.set(e.message),
      });
  }

  supprimer(c: Commentaire) {
    if (!confirm(`Supprimer le commentaire de ${c.auteur} ?`)) {
      return;
    }
    this.erreur.set(null);
    this.service.supprimer(c.id).subscribe({
      next: () => this.commentaires.reload(),
      error: (e: Error) => this.erreur.set(e.message),
    });
  }
}
