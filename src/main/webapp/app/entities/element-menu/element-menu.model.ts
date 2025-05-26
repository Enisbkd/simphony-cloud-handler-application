export interface IElementMenu {
  id: number;
  masterId?: number | null;
  nom?: string | null;
  nomCourt?: string | null;
  familyGroupRef?: number | null;
  prix?: number | null;
  menuRef?: number | null;
}

export type NewElementMenu = Omit<IElementMenu, 'id'> & { id: null };
