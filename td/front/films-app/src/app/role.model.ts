export interface Role {
    acteurId: number;
    firstname: string;
    name: string;
    personnage: string | null;
}

export interface RoleCreation {
    personnage: string;
}
