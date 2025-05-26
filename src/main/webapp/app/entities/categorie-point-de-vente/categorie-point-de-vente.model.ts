export interface ICategoriePointDeVente {
  id: number;
  categorie?: string | null;
  etablissementRef?: number | null;
}

export type NewCategoriePointDeVente = Omit<ICategoriePointDeVente, 'id'> & { id: null };
