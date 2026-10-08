import { Component, computed, effect, inject, input, signal } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { Router, RouterLink } from '@angular/router';
import { rxResource } from '@angular/core/rxjs-interop';
import { ActeurService } from '../acteur-service';
import { ActeurCreation } from '../acteur.model';

@Component({
  imports: [FormsModule, RouterLink],
  selector: 'app-acteur-form',
  styleUrl: './acteur-form.css',
  templateUrl: './acteur-form.html',
})
export class ActeurForm {
  private service = inject(ActeurService);
  private router = inject(Router);

  id = input<string>();
  acteurId = computed(() => (this.id() ? Number(this.id()) : undefined));
  edition = computed(() => this.acteurId() !== undefined);

  firstname = signal('');
  name = signal('');
  erreur = signal<string | null>(null);

  acteur = rxResource({
    params: () => this.acteurId(),
    stream: ({ params }) => this.service.getById(params),
  });

  constructor() {
    effect(() => {
      if (!this.acteur.hasValue()) {
        return;
      }
      const a = this.acteur.value();
      this.firstname.set(a.firstname);
      this.name.set(a.name);
    });
  }

  enregistrer() {
    const data: ActeurCreation = { firstname: this.firstname(), name: this.name() };
    const id = this.acteurId();
    const requete = id === undefined ? this.service.creer(data) : this.service.modifier(id, data);
    this.erreur.set(null);
    requete.subscribe({
      next: (a) => this.router.navigate(['/acteurs', a.id]),
      error: (e: Error) => this.erreur.set(e.message),
    });
  }
}
