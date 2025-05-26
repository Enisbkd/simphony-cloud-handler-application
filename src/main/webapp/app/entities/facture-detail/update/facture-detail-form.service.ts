import { Injectable } from '@angular/core';
import { FormControl, FormGroup, Validators } from '@angular/forms';

import dayjs from 'dayjs/esm';
import { DATE_TIME_FORMAT } from 'app/config/input.constants';
import { IFactureDetail, NewFactureDetail } from '../facture-detail.model';

/**
 * A partial Type with required key is used as form input.
 */
type PartialWithRequiredKeyOf<T extends { id: unknown }> = Partial<Omit<T, 'id'>> & { id: T['id'] };

/**
 * Type for createFormGroup and resetForm argument.
 * It accepts IFactureDetail for edit and NewFactureDetailFormGroupInput for create.
 */
type FactureDetailFormGroupInput = IFactureDetail | PartialWithRequiredKeyOf<NewFactureDetail>;

/**
 * Type that converts some properties for forms.
 */
type FormValueOf<T extends IFactureDetail | NewFactureDetail> = Omit<T, 'utcDateTime' | 'lclDateTime'> & {
  utcDateTime?: string | null;
  lclDateTime?: string | null;
};

type FactureDetailFormRawValue = FormValueOf<IFactureDetail>;

type NewFactureDetailFormRawValue = FormValueOf<NewFactureDetail>;

type FactureDetailFormDefaults = Pick<
  NewFactureDetail,
  'id' | 'utcDateTime' | 'lclDateTime' | 'estErreur' | 'estNul' | 'estRetourne' | 'estInvisible'
>;

type FactureDetailFormGroupContent = {
  id: FormControl<FactureDetailFormRawValue['id'] | NewFactureDetail['id']>;
  factureEnTeteRef: FormControl<FactureDetailFormRawValue['factureEnTeteRef']>;
  numLigne: FormControl<FactureDetailFormRawValue['numLigne']>;
  detailType: FormControl<FactureDetailFormRawValue['detailType']>;
  utcDateTime: FormControl<FactureDetailFormRawValue['utcDateTime']>;
  lclDateTime: FormControl<FactureDetailFormRawValue['lclDateTime']>;
  numSiege: FormControl<FactureDetailFormRawValue['numSiege']>;
  niveauPrix: FormControl<FactureDetailFormRawValue['niveauPrix']>;
  totalAffiche: FormControl<FactureDetailFormRawValue['totalAffiche']>;
  quantiteAffiche: FormControl<FactureDetailFormRawValue['quantiteAffiche']>;
  estErreur: FormControl<FactureDetailFormRawValue['estErreur']>;
  estNul: FormControl<FactureDetailFormRawValue['estNul']>;
  estRetourne: FormControl<FactureDetailFormRawValue['estRetourne']>;
  estInvisible: FormControl<FactureDetailFormRawValue['estInvisible']>;
  totalLigne: FormControl<FactureDetailFormRawValue['totalLigne']>;
  codeRaisonRef: FormControl<FactureDetailFormRawValue['codeRaisonRef']>;
  multiplicateur: FormControl<FactureDetailFormRawValue['multiplicateur']>;
  referenceInfo: FormControl<FactureDetailFormRawValue['referenceInfo']>;
  referenceInfo2: FormControl<FactureDetailFormRawValue['referenceInfo2']>;
  partieDeJourneeRef: FormControl<FactureDetailFormRawValue['partieDeJourneeRef']>;
  periodeDeServiceRef: FormControl<FactureDetailFormRawValue['periodeDeServiceRef']>;
  numChrono: FormControl<FactureDetailFormRawValue['numChrono']>;
  parentFactureDetailRef: FormControl<FactureDetailFormRawValue['parentFactureDetailRef']>;
  taxePourcentage: FormControl<FactureDetailFormRawValue['taxePourcentage']>;
  taxeMontant: FormControl<FactureDetailFormRawValue['taxeMontant']>;
  modePaiementTotal: FormControl<FactureDetailFormRawValue['modePaiementTotal']>;
  prix: FormControl<FactureDetailFormRawValue['prix']>;
  transactionEmployeRef: FormControl<FactureDetailFormRawValue['transactionEmployeRef']>;
  transfertEmployeRef: FormControl<FactureDetailFormRawValue['transfertEmployeRef']>;
  managerEmployeRef: FormControl<FactureDetailFormRawValue['managerEmployeRef']>;
  repasEmployeRef: FormControl<FactureDetailFormRawValue['repasEmployeRef']>;
  remiseRef: FormControl<FactureDetailFormRawValue['remiseRef']>;
  remiseElementMenuRef: FormControl<FactureDetailFormRawValue['remiseElementMenuRef']>;
  commissionServiceRef: FormControl<FactureDetailFormRawValue['commissionServiceRef']>;
  modePaiementRef: FormControl<FactureDetailFormRawValue['modePaiementRef']>;
  elementMenuRef: FormControl<FactureDetailFormRawValue['elementMenuRef']>;
};

export type FactureDetailFormGroup = FormGroup<FactureDetailFormGroupContent>;

@Injectable({ providedIn: 'root' })
export class FactureDetailFormService {
  createFactureDetailFormGroup(factureDetail: FactureDetailFormGroupInput = { id: null }): FactureDetailFormGroup {
    const factureDetailRawValue = this.convertFactureDetailToFactureDetailRawValue({
      ...this.getFormDefaults(),
      ...factureDetail,
    });
    return new FormGroup<FactureDetailFormGroupContent>({
      id: new FormControl(
        { value: factureDetailRawValue.id, disabled: true },
        {
          nonNullable: true,
          validators: [Validators.required],
        },
      ),
      factureEnTeteRef: new FormControl(factureDetailRawValue.factureEnTeteRef),
      numLigne: new FormControl(factureDetailRawValue.numLigne),
      detailType: new FormControl(factureDetailRawValue.detailType),
      utcDateTime: new FormControl(factureDetailRawValue.utcDateTime),
      lclDateTime: new FormControl(factureDetailRawValue.lclDateTime),
      numSiege: new FormControl(factureDetailRawValue.numSiege),
      niveauPrix: new FormControl(factureDetailRawValue.niveauPrix),
      totalAffiche: new FormControl(factureDetailRawValue.totalAffiche),
      quantiteAffiche: new FormControl(factureDetailRawValue.quantiteAffiche),
      estErreur: new FormControl(factureDetailRawValue.estErreur),
      estNul: new FormControl(factureDetailRawValue.estNul),
      estRetourne: new FormControl(factureDetailRawValue.estRetourne),
      estInvisible: new FormControl(factureDetailRawValue.estInvisible),
      totalLigne: new FormControl(factureDetailRawValue.totalLigne),
      codeRaisonRef: new FormControl(factureDetailRawValue.codeRaisonRef),
      multiplicateur: new FormControl(factureDetailRawValue.multiplicateur),
      referenceInfo: new FormControl(factureDetailRawValue.referenceInfo),
      referenceInfo2: new FormControl(factureDetailRawValue.referenceInfo2),
      partieDeJourneeRef: new FormControl(factureDetailRawValue.partieDeJourneeRef),
      periodeDeServiceRef: new FormControl(factureDetailRawValue.periodeDeServiceRef),
      numChrono: new FormControl(factureDetailRawValue.numChrono),
      parentFactureDetailRef: new FormControl(factureDetailRawValue.parentFactureDetailRef),
      taxePourcentage: new FormControl(factureDetailRawValue.taxePourcentage),
      taxeMontant: new FormControl(factureDetailRawValue.taxeMontant),
      modePaiementTotal: new FormControl(factureDetailRawValue.modePaiementTotal),
      prix: new FormControl(factureDetailRawValue.prix),
      transactionEmployeRef: new FormControl(factureDetailRawValue.transactionEmployeRef),
      transfertEmployeRef: new FormControl(factureDetailRawValue.transfertEmployeRef),
      managerEmployeRef: new FormControl(factureDetailRawValue.managerEmployeRef),
      repasEmployeRef: new FormControl(factureDetailRawValue.repasEmployeRef),
      remiseRef: new FormControl(factureDetailRawValue.remiseRef),
      remiseElementMenuRef: new FormControl(factureDetailRawValue.remiseElementMenuRef),
      commissionServiceRef: new FormControl(factureDetailRawValue.commissionServiceRef),
      modePaiementRef: new FormControl(factureDetailRawValue.modePaiementRef),
      elementMenuRef: new FormControl(factureDetailRawValue.elementMenuRef),
    });
  }

  getFactureDetail(form: FactureDetailFormGroup): IFactureDetail | NewFactureDetail {
    return this.convertFactureDetailRawValueToFactureDetail(form.getRawValue() as FactureDetailFormRawValue | NewFactureDetailFormRawValue);
  }

  resetForm(form: FactureDetailFormGroup, factureDetail: FactureDetailFormGroupInput): void {
    const factureDetailRawValue = this.convertFactureDetailToFactureDetailRawValue({ ...this.getFormDefaults(), ...factureDetail });
    form.reset(
      {
        ...factureDetailRawValue,
        id: { value: factureDetailRawValue.id, disabled: true },
      } as any /* cast to workaround https://github.com/angular/angular/issues/46458 */,
    );
  }

  private getFormDefaults(): FactureDetailFormDefaults {
    const currentTime = dayjs();

    return {
      id: null,
      utcDateTime: currentTime,
      lclDateTime: currentTime,
      estErreur: false,
      estNul: false,
      estRetourne: false,
      estInvisible: false,
    };
  }

  private convertFactureDetailRawValueToFactureDetail(
    rawFactureDetail: FactureDetailFormRawValue | NewFactureDetailFormRawValue,
  ): IFactureDetail | NewFactureDetail {
    return {
      ...rawFactureDetail,
      utcDateTime: dayjs(rawFactureDetail.utcDateTime, DATE_TIME_FORMAT),
      lclDateTime: dayjs(rawFactureDetail.lclDateTime, DATE_TIME_FORMAT),
    };
  }

  private convertFactureDetailToFactureDetailRawValue(
    factureDetail: IFactureDetail | (Partial<NewFactureDetail> & FactureDetailFormDefaults),
  ): FactureDetailFormRawValue | PartialWithRequiredKeyOf<NewFactureDetailFormRawValue> {
    return {
      ...factureDetail,
      utcDateTime: factureDetail.utcDateTime ? factureDetail.utcDateTime.format(DATE_TIME_FORMAT) : undefined,
      lclDateTime: factureDetail.lclDateTime ? factureDetail.lclDateTime.format(DATE_TIME_FORMAT) : undefined,
    };
  }
}
