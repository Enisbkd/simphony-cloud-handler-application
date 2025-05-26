import { Injectable } from '@angular/core';
import { FormControl, FormGroup, Validators } from '@angular/forms';

import { ITaxe, NewTaxe } from '../taxe.model';

/**
 * A partial Type with required key is used as form input.
 */
type PartialWithRequiredKeyOf<T extends { id: unknown }> = Partial<Omit<T, 'id'>> & { id: T['id'] };

/**
 * Type for createFormGroup and resetForm argument.
 * It accepts ITaxe for edit and NewTaxeFormGroupInput for create.
 */
type TaxeFormGroupInput = ITaxe | PartialWithRequiredKeyOf<NewTaxe>;

type TaxeFormDefaults = Pick<NewTaxe, 'id'>;

type TaxeFormGroupContent = {
  id: FormControl<ITaxe['id'] | NewTaxe['id']>;
  nom: FormControl<ITaxe['nom']>;
  nomCourt: FormControl<ITaxe['nomCourt']>;
  vatTaxRate: FormControl<ITaxe['vatTaxRate']>;
  classId: FormControl<ITaxe['classId']>;
  taxType: FormControl<ITaxe['taxType']>;
  etablissementRef: FormControl<ITaxe['etablissementRef']>;
};

export type TaxeFormGroup = FormGroup<TaxeFormGroupContent>;

@Injectable({ providedIn: 'root' })
export class TaxeFormService {
  createTaxeFormGroup(taxe: TaxeFormGroupInput = { id: null }): TaxeFormGroup {
    const taxeRawValue = {
      ...this.getFormDefaults(),
      ...taxe,
    };
    return new FormGroup<TaxeFormGroupContent>({
      id: new FormControl(
        { value: taxeRawValue.id, disabled: true },
        {
          nonNullable: true,
          validators: [Validators.required],
        },
      ),
      nom: new FormControl(taxeRawValue.nom),
      nomCourt: new FormControl(taxeRawValue.nomCourt),
      vatTaxRate: new FormControl(taxeRawValue.vatTaxRate),
      classId: new FormControl(taxeRawValue.classId),
      taxType: new FormControl(taxeRawValue.taxType),
      etablissementRef: new FormControl(taxeRawValue.etablissementRef, {
        validators: [Validators.required],
      }),
    });
  }

  getTaxe(form: TaxeFormGroup): ITaxe | NewTaxe {
    return form.getRawValue() as ITaxe | NewTaxe;
  }

  resetForm(form: TaxeFormGroup, taxe: TaxeFormGroupInput): void {
    const taxeRawValue = { ...this.getFormDefaults(), ...taxe };
    form.reset(
      {
        ...taxeRawValue,
        id: { value: taxeRawValue.id, disabled: true },
      } as any /* cast to workaround https://github.com/angular/angular/issues/46458 */,
    );
  }

  private getFormDefaults(): TaxeFormDefaults {
    return {
      id: null,
    };
  }
}
