export interface Patient {
    id: number;
    nom: string;
    prenom: string;
    dateNaissance: string; // le JSON transporte une chaîne ISO
    email: string;
}