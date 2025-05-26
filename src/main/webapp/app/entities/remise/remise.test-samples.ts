import { IRemise, NewRemise } from './remise.model';

export const sampleWithRequiredData: IRemise = {
  id: 8538,
  pointDeVenteRef: 3911,
};

export const sampleWithPartialData: IRemise = {
  id: 25985,
  value: 13746.5,
  pointDeVenteRef: 15214,
};

export const sampleWithFullData: IRemise = {
  id: 24424,
  nom: 'smuggle',
  nomCourt: 'slather',
  nomMstr: 'if queasily',
  typeValue: 'suspiciously whole',
  value: 9327.76,
  pointDeVenteRef: 6254,
};

export const sampleWithNewData: NewRemise = {
  pointDeVenteRef: 25899,
  id: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);
