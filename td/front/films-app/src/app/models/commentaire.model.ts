export interface Commentaire {
    id: number;
    auteur: string;
    date: string;
    message: string;
}

export type CommentaireCreation = Omit<Commentaire, 'id' | 'date'>;
