import { Genre } from './genre.model';
import { Role } from './role.model';

export interface Film {
    id: number;
    titre: string;
    realisateur: string;
    dateSortie: string;
    genre: Genre;
    acteurs?: Role[];
}

export type FilmCreation = Omit<Film, 'id' | 'acteurs'>;
