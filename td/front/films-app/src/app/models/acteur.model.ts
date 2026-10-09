import { Film } from './film.model';

export interface Acteur {
    id: number;
    firstname: string;
    name: string;
    films?: Film[];
}

export type ActeurCreation = Omit<Acteur, 'id' | 'films'>;
