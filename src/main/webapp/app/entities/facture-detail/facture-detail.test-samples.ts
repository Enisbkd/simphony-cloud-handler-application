import dayjs from 'dayjs/esm';

import { IFactureDetail, NewFactureDetail } from './facture-detail.model';

export const sampleWithRequiredData: IFactureDetail = {
  id: 4158,
};

export const sampleWithPartialData: IFactureDetail = {
  id: 16182,
  utcDateTime: dayjs('2025-05-26T13:37'),
  numSiege: 'with colorful brand',
  estNul: true,
  multiplicateur: 28414,
  referenceInfo: 'highlight colonialism',
  partieDeJourneeRef: 18697,
  numChrono: 26979,
  parentFactureDetailRef: 19599,
  modePaiementTotal: 6800,
  prix: 16874,
  remiseRef: 30432,
  remiseElementMenuRef: 27199,
  modePaiementRef: 10586,
  elementMenuRef: 23715,
};

export const sampleWithFullData: IFactureDetail = {
  id: 3914,
  factureEnTeteRef: 28179,
  numLigne: 16869,
  detailType: 'but',
  utcDateTime: dayjs('2025-05-26T12:26'),
  lclDateTime: dayjs('2025-05-26T05:44'),
  numSiege: 'ick modulo',
  niveauPrix: 5155.77,
  totalAffiche: 2190.62,
  quantiteAffiche: 29320,
  estErreur: true,
  estNul: false,
  estRetourne: false,
  estInvisible: true,
  totalLigne: 29585,
  codeRaisonRef: 13122,
  multiplicateur: 25233,
  referenceInfo: 'observe',
  referenceInfo2: 'judgementally helpfully',
  partieDeJourneeRef: 27372,
  periodeDeServiceRef: 29951,
  numChrono: 22667,
  parentFactureDetailRef: 1676,
  taxePourcentage: 27520,
  taxeMontant: 24509,
  modePaiementTotal: 25585,
  prix: 5673,
  transactionEmployeRef: 5368,
  transfertEmployeRef: 28009,
  managerEmployeRef: 30014,
  repasEmployeRef: 6410,
  remiseRef: 29223,
  remiseElementMenuRef: 23932,
  commissionServiceRef: 11580,
  modePaiementRef: 19907,
  elementMenuRef: 17728,
};

export const sampleWithNewData: NewFactureDetail = {
  id: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);
