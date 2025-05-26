import { IPartieDeJournee, NewPartieDeJournee } from './partie-de-journee.model';

export const sampleWithRequiredData: IPartieDeJournee = {
  id: 4094,
  nom: 'whoever lest gee',
};

export const sampleWithPartialData: IPartieDeJournee = {
  id: 22524,
  timeRangeStart: 'institute um eek',
  nom: 'mould',
};

export const sampleWithFullData: IPartieDeJournee = {
  id: 17512,
  timeRangeStart: 'brr turret',
  timeRangeEnd: 'thorny',
  nom: 'ick',
};

export const sampleWithNewData: NewPartieDeJournee = {
  nom: 'serve',
  id: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);
