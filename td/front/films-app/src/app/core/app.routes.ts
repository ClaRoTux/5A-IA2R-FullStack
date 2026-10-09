import { Routes } from '@angular/router';
import { FilmList } from '../features/films/film-list/film-list';
import { FilmForm } from '../features/films/film-form/film-form';
import { FilmDetail } from '../features/films/film-detail/film-detail';
import { ActeurList } from '../features/acteurs/acteur-list/acteur-list';
import { ActeurDetail } from '../features/acteurs/acteur-detail/acteur-detail';
import { NotFound } from '../shared/not-found/not-found';
import { ActeurForm } from '../features/acteurs/acteur-form/acteur-form';

export const routes: Routes = [
    { path: "films", component: FilmList },
    { path: "films/nouveau", component: FilmForm },
    { path: "films/:id/modifier", component: FilmForm },
    { path: "films/:id", component: FilmDetail },
    { path: "acteurs", component: ActeurList },
    { path: "acteurs/nouveau", component: ActeurForm },
    { path: "acteurs/:id/modifier", component: ActeurForm },
    { path: "acteurs/:id", component: ActeurDetail },
    { path: "", redirectTo: "films", pathMatch: "full" },
    { path: "**", component: NotFound },
];
