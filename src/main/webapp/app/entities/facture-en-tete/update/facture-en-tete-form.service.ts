import { Injectable } from '@angular/core';
import { FormControl, FormGroup, Validators } from '@angular/forms';

import dayjs from 'dayjs/esm';
import { DATE_TIME_FORMAT } from 'app/config/input.constants';
import { IFactureEnTete, NewFactureEnTete } from '../facture-en-tete.model';

/**
 * A partial Type with required key is used as form input.
 */
type PartialWithRequiredKeyOf<T extends { id: unknown }> = Partial<Omit<T, 'id'>> & { id: T['id'] };

/**
 * Type for createFormGroup and resetForm argument.
 * It accepts IFactureEnTete for edit and NewFactureEnTeteFormGroupInput for create.
 */
type FactureEnTeteFormGroupInput = IFactureEnTete | PartialWithRequiredKeyOf<NewFactureEnTete>;

/**
 * Type that converts some properties for forms.
 */
type FormValueOf<T extends IFactureEnTete | NewFactureEnTete> = Omit<T, 'ouvertureDateTime' | 'fermetureDateTime'> & {
  ouvertureDateTime?: string | null;
  fermetureDateTime?: string | null;
};

type FactureEnTeteFormRawValue = FormValueOf<IFactureEnTete>;

type NewFactureEnTeteFormRawValue = FormValueOf<NewFactureEnTete>;

type FactureEnTeteFormDefaults = Pick<NewFactureEnTete, 'id' | 'ouvertureDateTime' | 'fermetureDateTime' | 'estAnnule'>;

type FactureEnTeteFormGroupContent = {
  id: FormControl<FactureEnTeteFormRawValue['id'] | NewFactureEnTete['id']>;
  num: FormControl<FactureEnTeteFormRawValue['num']>;
  factureRef: FormControl<FactureEnTeteFormRawValue['factureRef']>;
  ouvertureDateTime: FormControl<FactureEnTeteFormRawValue['ouvertureDateTime']>;
  fermetureDateTime: FormControl<FactureEnTeteFormRawValue['fermetureDateTime']>;
  estAnnule: FormControl<FactureEnTeteFormRawValue['estAnnule']>;
  nbrePax: FormControl<FactureEnTeteFormRawValue['nbrePax']>;
  numTable: FormControl<FactureEnTeteFormRawValue['numTable']>;
  taxeMontantTotal: FormControl<FactureEnTeteFormRawValue['taxeMontantTotal']>;
  sousTotal: FormControl<FactureEnTeteFormRawValue['sousTotal']>;
  factureTotal: FormControl<FactureEnTeteFormRawValue['factureTotal']>;
  commissionTotal: FormControl<FactureEnTeteFormRawValue['commissionTotal']>;
  tipTotal: FormControl<FactureEnTeteFormRawValue['tipTotal']>;
  remiseTotal: FormControl<FactureEnTeteFormRawValue['remiseTotal']>;
  erreursCorrigeesTotal: FormControl<FactureEnTeteFormRawValue['erreursCorrigeesTotal']>;
  retourTotal: FormControl<FactureEnTeteFormRawValue['retourTotal']>;
  xferToFactureEnTeteRef: FormControl<FactureEnTeteFormRawValue['xferToFactureEnTeteRef']>;
  xferStatus: FormControl<FactureEnTeteFormRawValue['xferStatus']>;
  categoriePointDeVenteRef: FormControl<FactureEnTeteFormRawValue['categoriePointDeVenteRef']>;
  pointDeVenteRef: FormControl<FactureEnTeteFormRawValue['pointDeVenteRef']>;
};

export type FactureEnTeteFormGroup = FormGroup<FactureEnTeteFormGroupContent>;

@Injectable({ providedIn: 'root' })
export class FactureEnTeteFormService {
  createFactureEnTeteFormGroup(factureEnTete: FactureEnTeteFormGroupInput = { id: null }): FactureEnTeteFormGroup {
    const factureEnTeteRawValue = this.convertFactureEnTeteToFactureEnTeteRawValue({
      ...this.getFormDefaults(),
      ...factureEnTete,
    });
    return new FormGroup<FactureEnTeteFormGroupContent>({
      id: new FormControl(
        { value: factureEnTeteRawValue.id, disabled: true },
        {
          nonNullable: true,
          validators: [Validators.required],
        },
      ),
      num: new FormControl(factureEnTeteRawValue.num),
      factureRef: new FormControl(factureEnTeteRawValue.factureRef),
      ouvertureDateTime: new FormControl(factureEnTeteRawValue.ouvertureDateTime),
      fermetureDateTime: new FormControl(factureEnTeteRawValue.fermetureDateTime),
      estAnnule: new FormControl(factureEnTeteRawValue.estAnnule),
      nbrePax: new FormControl(factureEnTeteRawValue.nbrePax),
      numTable: new FormControl(factureEnTeteRawValue.numTable),
      taxeMontantTotal: new FormControl(factureEnTeteRawValue.taxeMontantTotal),
      sousTotal: new FormControl(factureEnTeteRawValue.sousTotal),
      factureTotal: new FormControl(factureEnTeteRawValue.factureTotal),
      commissionTotal: new FormControl(factureEnTeteRawValue.commissionTotal),
      tipTotal: new FormControl(factureEnTeteRawValue.tipTotal),
      remiseTotal: new FormControl(factureEnTeteRawValue.remiseTotal),
      erreursCorrigeesTotal: new FormControl(factureEnTeteRawValue.erreursCorrigeesTotal),
      retourTotal: new FormControl(factureEnTeteRawValue.retourTotal),
      xferToFactureEnTeteRef: new FormControl(factureEnTeteRawValue.xferToFactureEnTeteRef),
      xferStatus: new FormControl(factureEnTeteRawValue.xferStatus),
      categoriePointDeVenteRef: new FormControl(factureEnTeteRawValue.categoriePointDeVenteRef),
      pointDeVenteRef: new FormControl(factureEnTeteRawValue.pointDeVenteRef),
    });
  }

  getFactureEnTete(form: FactureEnTeteFormGroup): IFactureEnTete | NewFactureEnTete {
    return this.convertFactureEnTeteRawValueToFactureEnTete(form.getRawValue() as FactureEnTeteFormRawValue | NewFactureEnTeteFormRawValue);
  }

  resetForm(form: FactureEnTeteFormGroup, factureEnTete: FactureEnTeteFormGroupInput): void {
    const factureEnTeteRawValue = this.convertFactureEnTeteToFactureEnTeteRawValue({ ...this.getFormDefaults(), ...factureEnTete });
    form.reset(
      {
        ...factureEnTeteRawValue,
        id: { value: factureEnTeteRawValue.id, disabled: true },
      } as any /* cast to workaround https://github.com/angular/angular/issues/46458 */,
    );
  }

  private getFormDefaults(): FactureEnTeteFormDefaults {
    const currentTime = dayjs();

    return {
      id: null,
      ouvertureDateTime: currentTime,
      fermetureDateTime: currentTime,
      estAnnule: false,
    };
  }

  private convertFactureEnTeteRawValueToFactureEnTete(
    rawFactureEnTete: FactureEnTeteFormRawValue | NewFactureEnTeteFormRawValue,
  ): IFactureEnTete | NewFactureEnTete {
    return {
      ...rawFactureEnTete,
      ouvertureDateTime: dayjs(rawFactureEnTete.ouvertureDateTime, DATE_TIME_FORMAT),
      fermetureDateTime: dayjs(rawFactureEnTete.fermetureDateTime, DATE_TIME_FORMAT),
    };
  }

  private convertFactureEnTeteToFactureEnTeteRawValue(
    factureEnTete: IFactureEnTete | (Partial<NewFactureEnTete> & FactureEnTeteFormDefaults),
  ): FactureEnTeteFormRawValue | PartialWithRequiredKeyOf<NewFactureEnTeteFormRawValue> {
    return {
      ...factureEnTete,
      ouvertureDateTime: factureEnTete.ouvertureDateTime ? factureEnTete.ouvertureDateTime.format(DATE_TIME_FORMAT) : undefined,
      fermetureDateTime: factureEnTete.fermetureDateTime ? factureEnTete.fermetureDateTime.format(DATE_TIME_FORMAT) : undefined,
    };
  }
}
