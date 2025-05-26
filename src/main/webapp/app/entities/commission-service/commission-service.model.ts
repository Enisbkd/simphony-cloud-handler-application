export interface ICommissionService {
  id: number;
  nom?: string | null;
  nomCourt?: string | null;
  typeValue?: string | null;
  value?: number | null;
  etablissementRef?: string | null;
}

export type NewCommissionService = Omit<ICommissionService, 'id'> & { id: null };
