import { IMajorGroup, NewMajorGroup } from './major-group.model';

export const sampleWithRequiredData: IMajorGroup = {
  id: 4235,
  nom: 'before exhaust nifty',
  pointDeVenteRef: 28068,
};

export const sampleWithPartialData: IMajorGroup = {
  id: 31851,
  nom: 'another continually regulate',
  nomCourt: 'um',
  pointDeVenteRef: 27861,
};

export const sampleWithFullData: IMajorGroup = {
  id: 21304,
  nom: 'nor',
  nomCourt: 'wisely ugh',
  pointDeVenteRef: 29142,
};

export const sampleWithNewData: NewMajorGroup = {
  nom: 'obediently although upright',
  pointDeVenteRef: 22313,
  id: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);
