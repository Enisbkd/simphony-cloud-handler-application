import { ICodeRaison, NewCodeRaison } from './code-raison.model';

export const sampleWithRequiredData: ICodeRaison = {
  id: 24467,
  nomCourt: 31033,
  etablissementRef: 'even yellow',
};

export const sampleWithPartialData: ICodeRaison = {
  id: 15186,
  nomCourt: 25281,
  nomMstr: 'vicinity steeple surprise',
  numMstr: 30766,
  name: 'lavish yippee as',
  etablissementRef: 'repentant pleasure ruddy',
};

export const sampleWithFullData: ICodeRaison = {
  id: 17441,
  nomCourt: 25116,
  nomMstr: 'but extricate',
  numMstr: 30534,
  name: 'handful',
  etablissementRef: 'small obtrude',
};

export const sampleWithNewData: NewCodeRaison = {
  nomCourt: 17030,
  etablissementRef: 'meanwhile',
  id: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);
