export interface ICodeRaison {
  id: number;
  nomCourt?: number | null;
  nomMstr?: string | null;
  numMstr?: number | null;
  name?: string | null;
  etablissementRef?: string | null;
}

export type NewCodeRaison = Omit<ICodeRaison, 'id'> & { id: null };
