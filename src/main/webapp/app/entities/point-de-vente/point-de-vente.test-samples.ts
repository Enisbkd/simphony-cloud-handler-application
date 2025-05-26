import { IPointDeVente, NewPointDeVente } from './point-de-vente.model';

export const sampleWithRequiredData: IPointDeVente = {
  id: 8107,
  nom: 'vet though where',
  etablissementRef: 'pillory inquisitively',
  hierRef: 'too whoa finger',
};

export const sampleWithPartialData: IPointDeVente = {
  id: 10566,
  nom: 'pfft',
  estActif: true,
  etablissementRef: 'major oof repurpose',
  hierRef: 'yahoo',
};

export const sampleWithFullData: IPointDeVente = {
  id: 24056,
  nom: 'putrefy pacemaker',
  nomCourt: 'self-reliant corral why',
  estActif: false,
  etablissementRef: 'huzzah',
  hierRef: 'thunderbolt',
};

export const sampleWithNewData: NewPointDeVente = {
  nom: 'minus',
  etablissementRef: 'different',
  hierRef: 'fiercely into hateful',
  id: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);
