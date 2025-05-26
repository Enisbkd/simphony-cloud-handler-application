export interface IEtablissement {
  id: string;
  nom?: string | null;
  estGroupe?: boolean | null;
  sourceVersion?: string | null;
  societeRef?: string | null;
  hierRef?: string | null;
}

export type NewEtablissement = Omit<IEtablissement, 'id'> & { id: null };
