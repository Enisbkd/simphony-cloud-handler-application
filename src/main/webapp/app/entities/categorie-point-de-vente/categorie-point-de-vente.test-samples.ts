import { ICategoriePointDeVente, NewCategoriePointDeVente } from './categorie-point-de-vente.model';

export const sampleWithRequiredData: ICategoriePointDeVente = {
  id: 5298,
};

export const sampleWithPartialData: ICategoriePointDeVente = {
  id: 29020,
  categorie: 'wrongly',
};

export const sampleWithFullData: ICategoriePointDeVente = {
  id: 30003,
  categorie: 'instead',
  etablissementRef: 3081,
};

export const sampleWithNewData: NewCategoriePointDeVente = {
  id: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);
