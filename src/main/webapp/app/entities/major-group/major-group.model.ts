export interface IMajorGroup {
  id: number;
  nom?: string | null;
  nomCourt?: string | null;
  pointDeVenteRef?: number | null;
}

export type NewMajorGroup = Omit<IMajorGroup, 'id'> & { id: null };
