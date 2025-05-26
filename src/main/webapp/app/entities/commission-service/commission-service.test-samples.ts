import { ICommissionService, NewCommissionService } from './commission-service.model';

export const sampleWithRequiredData: ICommissionService = {
  id: 32706,
  etablissementRef: 'absent recklessly ack',
};

export const sampleWithPartialData: ICommissionService = {
  id: 17861,
  nom: 'at alienated orient',
  nomCourt: 'meaningfully brush',
  typeValue: 'dead irritably onto',
  etablissementRef: 'mammoth',
};

export const sampleWithFullData: ICommissionService = {
  id: 5842,
  nom: 'lest mad',
  nomCourt: 'boo sadly ecliptic',
  typeValue: 'deprave',
  value: 21472.7,
  etablissementRef: 'microblog membership approach',
};

export const sampleWithNewData: NewCommissionService = {
  etablissementRef: 'knight',
  id: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);
