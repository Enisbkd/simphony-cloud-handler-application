import { IFamilyGroup, NewFamilyGroup } from './family-group.model';

export const sampleWithRequiredData: IFamilyGroup = {
  id: 10934,
  nomCourt: 'heavily knowingly',
};

export const sampleWithPartialData: IFamilyGroup = {
  id: 6335,
  nom: 'intrigue vibraphone boo',
  nomCourt: 'demonstrate',
  majorGroupRef: 24380,
};

export const sampleWithFullData: IFamilyGroup = {
  id: 18934,
  nom: 'duh amend farmer',
  nomCourt: 'until why mom',
  majorGroupRef: 13352,
};

export const sampleWithNewData: NewFamilyGroup = {
  nomCourt: 'indeed',
  id: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);
