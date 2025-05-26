export interface IEmploye {
  id: number;
  num?: number | null;
  firstName?: string | null;
  lastName?: string | null;
  userName?: string | null;
  etablissementRef?: string | null;
}

export type NewEmploye = Omit<IEmploye, 'id'> & { id: null };
