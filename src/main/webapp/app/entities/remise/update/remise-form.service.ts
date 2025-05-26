import { Injectable } from '@angular/core';
import { FormControl, FormGroup, Validators } from '@angular/forms';

import { IRemise, NewRemise } from '../remise.model';

/**
 * A partial Type with required key is used as form input.
 */
type PartialWithRequiredKeyOf<T extends { id: unknown }> = Partial<Omit<T, 'id'>> & { id: T['id'] };

/**
 * Type for createFormGroup and resetForm argument.
 * It accepts IRemise for edit and NewRemiseFormGroupInput for create.
 */
type RemiseFormGroupInput = IRemise | PartialWithRequiredKeyOf<NewRemise>;

type RemiseFormDefaults = Pick<NewRemise, 'id'>;

type RemiseFormGroupContent = {
  id: FormControl<IRemise['id'] | NewRemise['id']>;
  nom: FormControl<IRemise['nom']>;
  nomCourt: FormControl<IRemise['nomCourt']>;
  nomMstr: FormControl<IRemise['nomMstr']>;
  typeValue: FormControl<IRemise['typeValue']>;
  value: FormControl<IRemise['value']>;
  pointDeVenteRef: FormControl<IRemise['pointDeVenteRef']>;
};

export type RemiseFormGroup = FormGroup<RemiseFormGroupContent>;

@Injectable({ providedIn: 'root' })
export class RemiseFormService {
  createRemiseFormGroup(remise: RemiseFormGroupInput = { id: null }): RemiseFormGroup {
    const remiseRawValue = {
      ...this.getFormDefaults(),
      ...remise,
    };
    return new FormGroup<RemiseFormGroupContent>({
      id: new FormControl(
        { value: remiseRawValue.id, disabled: true },
        {
          nonNullable: true,
          validators: [Validators.required],
        },
      ),
      nom: new FormControl(remiseRawValue.nom),
      nomCourt: new FormControl(remiseRawValue.nomCourt),
      nomMstr: new FormControl(remiseRawValue.nomMstr),
      typeValue: new FormControl(remiseRawValue.typeValue),
      value: new FormControl(remiseRawValue.value),
      pointDeVenteRef: new FormControl(remiseRawValue.pointDeVenteRef, {
        validators: [Validators.required],
      }),
    });
  }

  getRemise(form: RemiseFormGroup): IRemise | NewRemise {
    return form.getRawValue() as IRemise | NewRemise;
  }

  resetForm(form: RemiseFormGroup, remise: RemiseFormGroupInput): void {
    const remiseRawValue = { ...this.getFormDefaults(), ...remise };
    form.reset(
      {
        ...remiseRawValue,
        id: { value: remiseRawValue.id, disabled: true },
      } as any /* cast to workaround https://github.com/angular/angular/issues/46458 */,
    );
  }

  private getFormDefaults(): RemiseFormDefaults {
    return {
      id: null,
    };
  }
}
