import dayjs from 'dayjs/esm';

export interface IFactureDetail {
  id: number;
  factureEnTeteRef?: number | null;
  numLigne?: number | null;
  detailType?: string | null;
  utcDateTime?: dayjs.Dayjs | null;
  lclDateTime?: dayjs.Dayjs | null;
  numSiege?: string | null;
  niveauPrix?: number | null;
  totalAffiche?: number | null;
  quantiteAffiche?: number | null;
  estErreur?: boolean | null;
  estNul?: boolean | null;
  estRetourne?: boolean | null;
  estInvisible?: boolean | null;
  totalLigne?: number | null;
  codeRaisonRef?: number | null;
  multiplicateur?: number | null;
  referenceInfo?: string | null;
  referenceInfo2?: string | null;
  partieDeJourneeRef?: number | null;
  periodeDeServiceRef?: number | null;
  numChrono?: number | null;
  parentFactureDetailRef?: number | null;
  taxePourcentage?: number | null;
  taxeMontant?: number | null;
  modePaiementTotal?: number | null;
  prix?: number | null;
  transactionEmployeRef?: number | null;
  transfertEmployeRef?: number | null;
  managerEmployeRef?: number | null;
  repasEmployeRef?: number | null;
  remiseRef?: number | null;
  remiseElementMenuRef?: number | null;
  commissionServiceRef?: number | null;
  modePaiementRef?: number | null;
  elementMenuRef?: number | null;
}

export type NewFactureDetail = Omit<IFactureDetail, 'id'> & { id: null };
