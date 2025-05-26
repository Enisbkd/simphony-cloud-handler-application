import { Injectable } from '@angular/core';
import { FormControl, FormGroup, Validators } from '@angular/forms';

import { IEtablissement, NewEtablissement } from '../etablissement.model';

/**
 * A partial Type with required key is used as form input.
 */
type PartialWithRequiredKeyOf<T extends { id: unknown }> = Partial<Omit<T, 'id'>> & { id: T['id'] };

/**
 * Type for createFormGroup and resetForm argument.
 * It accepts IEtablissement for edit and NewEtablissementFormGroupInput for create.
 */
type EtablissementFormGroupInput = IEtablissement | PartialWithRequiredKeyOf<NewEtablissement>;

type EtablissementFormDefaults = Pick<NewEtablissement, 'id' | 'estGroupe'>;

type EtablissementFormGroupContent = {
  id: FormControl<IEtablissement['id'] | NewEtablissement['id']>;
  nom: FormControl<IEtablissement['nom']>;
  estGroupe: FormControl<IEtablissement['estGroupe']>;
  sourceVersion: FormControl<IEtablissement['sourceVersion']>;
  societeRef: FormControl<IEtablissement['societeRef']>;
  hierRef: FormControl<IEtablissement['hierRef']>;
};

export type EtablissementFormGroup = FormGroup<EtablissementFormGroupContent>;

@Injectable({ providedIn: 'root' })
export class EtablissementFormService {
  createEtablissementFormGroup(etablissement: EtablissementFormGroupInput = { id: null }): EtablissementFormGroup {
    const etablissementRawValue = {
      ...this.getFormDefaults(),
      ...etablissement,
    };
    return new FormGroup<EtablissementFormGroupContent>({
      id: new FormControl(
        { value: etablissementRawValue.id, disabled: true },
        {
          nonNullable: true,
          validators: [Validators.required],
        },
      ),
      nom: new FormControl(etablissementRawValue.nom, {
        validators: [Validators.required],
      }),
      estGroupe: new FormControl(etablissementRawValue.estGroupe),
      sourceVersion: new FormControl(etablissementRawValue.sourceVersion),
      societeRef: new FormControl(etablissementRawValue.societeRef, {
        validators: [Validators.required],
      }),
      hierRef: new FormControl(etablissementRawValue.hierRef, {
        validators: [Validators.required],
      }),
    });
  }

  getEtablissement(form: EtablissementFormGroup): IEtablissement | NewEtablissement {
    return form.getRawValue() as IEtablissement | NewEtablissement;
  }

  resetForm(form: EtablissementFormGroup, etablissement: EtablissementFormGroupInput): void {
    const etablissementRawValue = { ...this.getFormDefaults(), ...etablissement };
    form.reset(
      {
        ...etablissementRawValue,
        id: { value: etablissementRawValue.id, disabled: true },
      } as any /* cast to workaround https://github.com/angular/angular/issues/46458 */,
    );
  }

  private getFormDefaults(): EtablissementFormDefaults {
    return {
      id: null,
      estGroupe: false,
    };
  }
}
