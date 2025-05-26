export interface ITaxe {
  id: number;
  nom?: string | null;
  nomCourt?: string | null;
  vatTaxRate?: number | null;
  classId?: number | null;
  taxType?: number | null;
  etablissementRef?: string | null;
}

export type NewTaxe = Omit<ITaxe, 'id'> & { id: null };
