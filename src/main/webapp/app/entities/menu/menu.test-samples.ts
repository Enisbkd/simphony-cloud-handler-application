import { IMenu, NewMenu } from './menu.model';

export const sampleWithRequiredData: IMenu = {
  id: 26476,
  nom: 'yak whine',
  etablissementRef: 'unsung bungalow',
};

export const sampleWithPartialData: IMenu = {
  id: 15461,
  nom: 'because conceal substantiate',
  description: 'puppet',
  etablissementRef: 'ice-cream preregister',
};

export const sampleWithFullData: IMenu = {
  id: 277,
  nom: 'wilt honesty',
  description: 'after',
  etablissementRef: 'phew',
};

export const sampleWithNewData: NewMenu = {
  nom: 'supposing finally',
  etablissementRef: 'outfox till',
  id: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);
