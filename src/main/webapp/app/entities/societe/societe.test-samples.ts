import { ISociete, NewSociete } from './societe.model';

export const sampleWithRequiredData: ISociete = {
  id: 'fdfb5958-ed4b-4bc9-9679-ef7645ca89cf',
};

export const sampleWithPartialData: ISociete = {
  id: 'dd74c6bb-3db2-4860-953e-5c1a615a84bd',
};

export const sampleWithFullData: ISociete = {
  id: '7e9c65b7-e605-4965-b68a-febd54e5e2e2',
  nom: 'searchingly adjourn',
  nomCourt: 'bludgeon ick',
};

export const sampleWithNewData: NewSociete = {
  id: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);
