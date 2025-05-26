export interface IHier {
  id: string;
  nom?: string | null;
  parentHierId?: string | null;
  unitId?: string | null;
}

export type NewHier = Omit<IHier, 'id'> & { id: null };
