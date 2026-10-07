export const GENRES = [
    'ACTION',
    'ANIMATION',
    'AVENTURE',
    'COMEDIE',
    'DOCUMENTAIRE',
    'DRAME',
    'FANTASTIQUE',
    'HORREUR',
    'POLICIER',
    'ROMANCE',
    'SCIENCE_FICTION',
    'THRILLER',
    'WESTERN',
] as const;

export type Genre = (typeof GENRES)[number];
