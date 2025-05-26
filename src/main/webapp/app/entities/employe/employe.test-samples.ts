import { IEmploye, NewEmploye } from './employe.model';

export const sampleWithRequiredData: IEmploye = {
  id: 27930,
  num: 12195,
  etablissementRef: 'long',
};

export const sampleWithPartialData: IEmploye = {
  id: 3534,
  num: 11058,
  firstName: 'Kiley',
  lastName: 'Schroeder',
  userName: 'compete eggplant gracefully',
  etablissementRef: 'why hence boo',
};

export const sampleWithFullData: IEmploye = {
  id: 9509,
  num: 23803,
  firstName: 'Leora',
  lastName: 'Goyette',
  userName: 'hurtful spherical forenenst',
  etablissementRef: 'furthermore stealthily quietly',
};

export const sampleWithNewData: NewEmploye = {
  num: 31923,
  etablissementRef: 'yahoo',
  id: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);
