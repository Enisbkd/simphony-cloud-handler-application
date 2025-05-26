import { IHier, NewHier } from './hier.model';

export const sampleWithRequiredData: IHier = {
  id: '0d34b1d6-b893-4d1b-bf64-597657ae0ef7',
  nom: 'whenever',
};

export const sampleWithPartialData: IHier = {
  id: '340239b8-69e9-429c-8bff-3ec94b53edc6',
  nom: 'mid knottily',
  parentHierId: 'kaleidoscopic meh',
  unitId: 'before',
};

export const sampleWithFullData: IHier = {
  id: '359559d6-0b15-431c-98bb-6107d7304629',
  nom: 'rudely afore',
  parentHierId: 'polished for blah',
  unitId: 'provided',
};

export const sampleWithNewData: NewHier = {
  nom: 'yet behind furthermore',
  id: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);
