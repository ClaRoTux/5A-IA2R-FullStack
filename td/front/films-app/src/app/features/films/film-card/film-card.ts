import { Component, computed, input, output } from '@angular/core';
import { DatePipe, NgClass } from '@angular/common';
import { RouterLink } from '@angular/router';
import { Film } from '../../../models/film.model';

@Component({
  imports: [RouterLink, DatePipe, NgClass],
  selector: 'app-film-card',
  styleUrl: './film-card.css',
  templateUrl: './film-card.html',
})
export class FilmCard {
  film = input.required<Film>();
  supprimer = output<Film>();

  avant2000 = computed(() => new Date(this.film().dateSortie).getFullYear() < 2000);

  onSupprimer() {
    this.supprimer.emit(this.film());
  }
}
