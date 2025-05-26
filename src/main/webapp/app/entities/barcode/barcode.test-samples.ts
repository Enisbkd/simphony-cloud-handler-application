import { IBarcode, NewBarcode } from './barcode.model';

export const sampleWithRequiredData: IBarcode = {
  id: 8695,
  pointDeVenteRef: 13509,
};

export const sampleWithPartialData: IBarcode = {
  id: 12234,
  barcode: 'but sans ew',
  prix: 11538,
  defNumSequence: 14066,
  pointDeVenteRef: 29701,
};

export const sampleWithFullData: IBarcode = {
  id: 4916,
  num: 2657,
  barcode: 'inwardly',
  prix: 4264,
  coutPreparation: 6309,
  defNumSequence: 12909,
  prixNumSequence: 15854,
  pointDeVenteRef: 18113,
  elementMenuRef: 11939,
};

export const sampleWithNewData: NewBarcode = {
  pointDeVenteRef: 2399,
  id: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);
