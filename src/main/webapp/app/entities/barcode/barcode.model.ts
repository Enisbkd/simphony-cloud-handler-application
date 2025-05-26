export interface IBarcode {
  id: number;
  num?: number | null;
  barcode?: string | null;
  prix?: number | null;
  coutPreparation?: number | null;
  defNumSequence?: number | null;
  prixNumSequence?: number | null;
  pointDeVenteRef?: number | null;
  elementMenuRef?: number | null;
}

export type NewBarcode = Omit<IBarcode, 'id'> & { id: null };
