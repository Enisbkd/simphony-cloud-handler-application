import dayjs from 'dayjs/esm';

import { IFactureEnTete, NewFactureEnTete } from './facture-en-tete.model';

export const sampleWithRequiredData: IFactureEnTete = {
  id: 25171,
};

export const sampleWithPartialData: IFactureEnTete = {
  id: 682,
  num: 3202,
  ouvertureDateTime: dayjs('2025-05-26T04:07'),
  nbrePax: 9349,
  factureTotal: 9434,
  commissionTotal: 23583,
  tipTotal: 15192,
  xferStatus: 'requite',
  categoriePointDeVenteRef: 24866,
  pointDeVenteRef: 18362,
};

export const sampleWithFullData: IFactureEnTete = {
  id: 25992,
  num: 17366,
  factureRef: 'ew',
  ouvertureDateTime: dayjs('2025-05-25T18:54'),
  fermetureDateTime: dayjs('2025-05-26T12:54'),
  estAnnule: false,
  nbrePax: 25866,
  numTable: 27116,
  taxeMontantTotal: 10491,
  sousTotal: 5722,
  factureTotal: 24077,
  commissionTotal: 10903,
  tipTotal: 4527,
  remiseTotal: 8389,
  erreursCorrigeesTotal: 21523,
  retourTotal: 672,
  xferToFactureEnTeteRef: 28347,
  xferStatus: 'unlike',
  categoriePointDeVenteRef: 29125,
  pointDeVenteRef: 2691,
};

export const sampleWithNewData: NewFactureEnTete = {
  id: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);
