export interface IPointDeVente {
  id: number;
  nom?: string | null;
  nomCourt?: string | null;
  estActif?: boolean | null;
  etablissementRef?: string | null;
  hierRef?: string | null;
}

export type NewPointDeVente = Omit<IPointDeVente, 'id'> & { id: null };
