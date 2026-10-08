import { Component, inject, signal } from '@angular/core';
import { RouterLink } from '@angular/router';
import { ActeurService } from '../acteur-service';
import { toSignal } from '@angular/core/rxjs-interop';
import { catchError, of } from 'rxjs';

@Component({
  imports: [RouterLink],
  selector: 'app-acteur-list',
  styleUrl: './acteur-list.css',
  templateUrl: './acteur-list.html',
})
export class ActeurList {
  private service = inject(ActeurService);
  erreur = signal<string | null>(null);
  acteurs = toSignal(
    this.service.getAll().pipe(
      catchError((e: Error) => {
        this.erreur.set(e.message);
        return of([]);
      }),
    ), { initialValue: [] },
  );
}
