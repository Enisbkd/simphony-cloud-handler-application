export interface IRemise {
  id: number;
  nom?: string | null;
  nomCourt?: string | null;
  nomMstr?: string | null;
  typeValue?: string | null;
  value?: number | null;
  pointDeVenteRef?: number | null;
}

export type NewRemise = Omit<IRemise, 'id'> & { id: null };
