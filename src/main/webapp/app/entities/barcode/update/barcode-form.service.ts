import { Injectable } from '@angular/core';
import { FormControl, FormGroup, Validators } from '@angular/forms';

import { IBarcode, NewBarcode } from '../barcode.model';

/**
 * A partial Type with required key is used as form input.
 */
type PartialWithRequiredKeyOf<T extends { id: unknown }> = Partial<Omit<T, 'id'>> & { id: T['id'] };

/**
 * Type for createFormGroup and resetForm argument.
 * It accepts IBarcode for edit and NewBarcodeFormGroupInput for create.
 */
type BarcodeFormGroupInput = IBarcode | PartialWithRequiredKeyOf<NewBarcode>;

type BarcodeFormDefaults = Pick<NewBarcode, 'id'>;

type BarcodeFormGroupContent = {
  id: FormControl<IBarcode['id'] | NewBarcode['id']>;
  num: FormControl<IBarcode['num']>;
  barcode: FormControl<IBarcode['barcode']>;
  prix: FormControl<IBarcode['prix']>;
  coutPreparation: FormControl<IBarcode['coutPreparation']>;
  defNumSequence: FormControl<IBarcode['defNumSequence']>;
  prixNumSequence: FormControl<IBarcode['prixNumSequence']>;
  pointDeVenteRef: FormControl<IBarcode['pointDeVenteRef']>;
  elementMenuRef: FormControl<IBarcode['elementMenuRef']>;
};

export type BarcodeFormGroup = FormGroup<BarcodeFormGroupContent>;

@Injectable({ providedIn: 'root' })
export class BarcodeFormService {
  createBarcodeFormGroup(barcode: BarcodeFormGroupInput = { id: null }): BarcodeFormGroup {
    const barcodeRawValue = {
      ...this.getFormDefaults(),
      ...barcode,
    };
    return new FormGroup<BarcodeFormGroupContent>({
      id: new FormControl(
        { value: barcodeRawValue.id, disabled: true },
        {
          nonNullable: true,
          validators: [Validators.required],
        },
      ),
      num: new FormControl(barcodeRawValue.num),
      barcode: new FormControl(barcodeRawValue.barcode),
      prix: new FormControl(barcodeRawValue.prix),
      coutPreparation: new FormControl(barcodeRawValue.coutPreparation),
      defNumSequence: new FormControl(barcodeRawValue.defNumSequence),
      prixNumSequence: new FormControl(barcodeRawValue.prixNumSequence),
      pointDeVenteRef: new FormControl(barcodeRawValue.pointDeVenteRef, {
        validators: [Validators.required],
      }),
      elementMenuRef: new FormControl(barcodeRawValue.elementMenuRef),
    });
  }

  getBarcode(form: BarcodeFormGroup): IBarcode | NewBarcode {
    return form.getRawValue() as IBarcode | NewBarcode;
  }

  resetForm(form: BarcodeFormGroup, barcode: BarcodeFormGroupInput): void {
    const barcodeRawValue = { ...this.getFormDefaults(), ...barcode };
    form.reset(
      {
        ...barcodeRawValue,
        id: { value: barcodeRawValue.id, disabled: true },
      } as any /* cast to workaround https://github.com/angular/angular/issues/46458 */,
    );
  }

  private getFormDefaults(): BarcodeFormDefaults {
    return {
      id: null,
    };
  }
}
