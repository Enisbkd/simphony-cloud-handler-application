export interface ISociete {
  id: string;
  nom?: string | null;
  nomCourt?: string | null;
}

export type NewSociete = Omit<ISociete, 'id'> & { id: null };
