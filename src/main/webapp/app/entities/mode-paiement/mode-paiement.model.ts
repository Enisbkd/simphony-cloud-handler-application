export interface IModePaiement {
  id: number;
  nom?: string | null;
  nomCourt?: string | null;
  nomMstr?: string | null;
  type?: string | null;
  etablissementRef?: string | null;
}

export type NewModePaiement = Omit<IModePaiement, 'id'> & { id: null };
