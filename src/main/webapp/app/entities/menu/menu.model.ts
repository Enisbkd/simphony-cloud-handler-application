export interface IMenu {
  id: number;
  nom?: string | null;
  description?: string | null;
  etablissementRef?: string | null;
}

export type NewMenu = Omit<IMenu, 'id'> & { id: null };
