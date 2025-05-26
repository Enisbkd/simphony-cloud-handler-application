import { Injectable } from '@angular/core';
import { FormControl, FormGroup, Validators } from '@angular/forms';

import { IModePaiement, NewModePaiement } from '../mode-paiement.model';

/**
 * A partial Type with required key is used as form input.
 */
type PartialWithRequiredKeyOf<T extends { id: unknown }> = Partial<Omit<T, 'id'>> & { id: T['id'] };

/**
 * Type for createFormGroup and resetForm argument.
 * It accepts IModePaiement for edit and NewModePaiementFormGroupInput for create.
 */
type ModePaiementFormGroupInput = IModePaiement | PartialWithRequiredKeyOf<NewModePaiement>;

type ModePaiementFormDefaults = Pick<NewModePaiement, 'id'>;

type ModePaiementFormGroupContent = {
  id: FormControl<IModePaiement['id'] | NewModePaiement['id']>;
  nom: FormControl<IModePaiement['nom']>;
  nomCourt: FormControl<IModePaiement['nomCourt']>;
  nomMstr: FormControl<IModePaiement['nomMstr']>;
  type: FormControl<IModePaiement['type']>;
  etablissementRef: FormControl<IModePaiement['etablissementRef']>;
};

export type ModePaiementFormGroup = FormGroup<ModePaiementFormGroupContent>;

@Injectable({ providedIn: 'root' })
export class ModePaiementFormService {
  createModePaiementFormGroup(modePaiement: ModePaiementFormGroupInput = { id: null }): ModePaiementFormGroup {
    const modePaiementRawValue = {
      ...this.getFormDefaults(),
      ...modePaiement,
    };
    return new FormGroup<ModePaiementFormGroupContent>({
      id: new FormControl(
        { value: modePaiementRawValue.id, disabled: true },
        {
          nonNullable: true,
          validators: [Validators.required],
        },
      ),
      nom: new FormControl(modePaiementRawValue.nom),
      nomCourt: new FormControl(modePaiementRawValue.nomCourt),
      nomMstr: new FormControl(modePaiementRawValue.nomMstr),
      type: new FormControl(modePaiementRawValue.type),
      etablissementRef: new FormControl(modePaiementRawValue.etablissementRef, {
        validators: [Validators.required],
      }),
    });
  }

  getModePaiement(form: ModePaiementFormGroup): IModePaiement | NewModePaiement {
    return form.getRawValue() as IModePaiement | NewModePaiement;
  }

  resetForm(form: ModePaiementFormGroup, modePaiement: ModePaiementFormGroupInput): void {
    const modePaiementRawValue = { ...this.getFormDefaults(), ...modePaiement };
    form.reset(
      {
        ...modePaiementRawValue,
        id: { value: modePaiementRawValue.id, disabled: true },
      } as any /* cast to workaround https://github.com/angular/angular/issues/46458 */,
    );
  }

  private getFormDefaults(): ModePaiementFormDefaults {
    return {
      id: null,
    };
  }
}
