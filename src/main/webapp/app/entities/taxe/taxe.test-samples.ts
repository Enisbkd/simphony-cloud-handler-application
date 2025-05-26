import { ITaxe, NewTaxe } from './taxe.model';

export const sampleWithRequiredData: ITaxe = {
  id: 9869,
  etablissementRef: 'like',
};

export const sampleWithPartialData: ITaxe = {
  id: 25263,
  vatTaxRate: 27498,
  classId: 22228,
  etablissementRef: 'pfft swiftly',
};

export const sampleWithFullData: ITaxe = {
  id: 16484,
  nom: 'yowza till stir-fry',
  nomCourt: 'briskly the',
  vatTaxRate: 27104,
  classId: 19576,
  taxType: 25889,
  etablissementRef: 'into',
};

export const sampleWithNewData: NewTaxe = {
  etablissementRef: 'even since aboard',
  id: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);
