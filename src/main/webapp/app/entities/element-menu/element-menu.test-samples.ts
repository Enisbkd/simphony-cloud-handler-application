import { IElementMenu, NewElementMenu } from './element-menu.model';

export const sampleWithRequiredData: IElementMenu = {
  id: 10299,
  masterId: 23605,
  nom: 'focused blond',
  familyGroupRef: 12313,
  menuRef: 22657,
};

export const sampleWithPartialData: IElementMenu = {
  id: 2378,
  masterId: 11875,
  nom: 'apud highlight',
  nomCourt: 'junior delicious midst',
  familyGroupRef: 10095,
  menuRef: 9089,
};

export const sampleWithFullData: IElementMenu = {
  id: 11084,
  masterId: 29656,
  nom: 'sweetly',
  nomCourt: 'retrospectivity',
  familyGroupRef: 1141,
  prix: 25854,
  menuRef: 582,
};

export const sampleWithNewData: NewElementMenu = {
  masterId: 18936,
  nom: 'mostly',
  familyGroupRef: 2654,
  menuRef: 19461,
  id: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);
