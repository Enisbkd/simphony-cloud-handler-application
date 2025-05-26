import dayjs from 'dayjs/esm';

export interface IFactureEnTete {
  id: number;
  num?: number | null;
  factureRef?: string | null;
  ouvertureDateTime?: dayjs.Dayjs | null;
  fermetureDateTime?: dayjs.Dayjs | null;
  estAnnule?: boolean | null;
  nbrePax?: number | null;
  numTable?: number | null;
  taxeMontantTotal?: number | null;
  sousTotal?: number | null;
  factureTotal?: number | null;
  commissionTotal?: number | null;
  tipTotal?: number | null;
  remiseTotal?: number | null;
  erreursCorrigeesTotal?: number | null;
  retourTotal?: number | null;
  xferToFactureEnTeteRef?: number | null;
  xferStatus?: string | null;
  categoriePointDeVenteRef?: number | null;
  pointDeVenteRef?: number | null;
}

export type NewFactureEnTete = Omit<IFactureEnTete, 'id'> & { id: null };
