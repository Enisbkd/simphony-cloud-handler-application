export interface IFamilyGroup {
  id: number;
  nom?: string | null;
  nomCourt?: string | null;
  majorGroupRef?: number | null;
}

export type NewFamilyGroup = Omit<IFamilyGroup, 'id'> & { id: null };
