import { IModePaiement, NewModePaiement } from './mode-paiement.model';

export const sampleWithRequiredData: IModePaiement = {
  id: 3291,
  etablissementRef: 'misreport accidentally nor',
};

export const sampleWithPartialData: IModePaiement = {
  id: 1389,
  nom: 'till anenst',
  nomCourt: 'likely understated',
  nomMstr: 'whose around via',
  etablissementRef: 'indeed an in',
};

export const sampleWithFullData: IModePaiement = {
  id: 18626,
  nom: 'amount',
  nomCourt: 'square physically aw',
  nomMstr: 'duh after schedule',
  type: 'among remark a',
  etablissementRef: 'times except',
};

export const sampleWithNewData: NewModePaiement = {
  etablissementRef: 'sunny march',
  id: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);
