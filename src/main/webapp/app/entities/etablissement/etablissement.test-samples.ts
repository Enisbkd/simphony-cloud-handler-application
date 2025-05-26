import { IEtablissement, NewEtablissement } from './etablissement.model';

export const sampleWithRequiredData: IEtablissement = {
  id: '0fe9cf4d-6079-4385-ab01-3d45806ccca5',
  nom: 'polyester since chapel',
  societeRef: 'yowza',
  hierRef: 'but scorn',
};

export const sampleWithPartialData: IEtablissement = {
  id: 'ee9ff880-217c-442b-aebb-dd38c28bdf38',
  nom: 'relative comb',
  sourceVersion: 'ferociously obscure',
  societeRef: 'aw hmph',
  hierRef: 'gah runny',
};

export const sampleWithFullData: IEtablissement = {
  id: '95ddf618-bbb8-4f1a-b687-0d19c425d96b',
  nom: 'smooth',
  estGroupe: false,
  sourceVersion: 'against',
  societeRef: 'why roadway though',
  hierRef: 'fooey',
};

export const sampleWithNewData: NewEtablissement = {
  nom: 'till ethical huzzah',
  societeRef: 'beside very',
  hierRef: 'geez pilot soon',
  id: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);
