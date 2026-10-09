import { Component, inject, signal } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { RouterLink } from '@angular/router';
import { rxResource } from '@angular/core/rxjs-interop';
import { ActeurService } from '../../../services/acteur-service';
import { Pagination } from '../../../shared/pagination/pagination';

@Component({
  imports: [RouterLink, FormsModule, Pagination],
  selector: 'app-acteur-list',
  styleUrl: './acteur-list.css',
  templateUrl: './acteur-list.html',
})
export class ActeurList {
  private service = inject(ActeurService);
  private taille = 10;

  page = signal(0);
  tri = signal('name,asc');
  recherche = signal('');

  acteurs = rxResource({
    params: () => ({ page: this.page(), tri: this.tri(), recherche: this.recherche() }),
    stream: ({ params }) =>
      this.service.getPage(params.page, this.taille, params.tri, params.recherche),
  });

  changerTri(tri: string) {
    this.tri.set(tri);
    this.page.set(0);
  }

  changerRecherche(recherche: string) {
    this.recherche.set(recherche);
    this.page.set(0);
  }
}
