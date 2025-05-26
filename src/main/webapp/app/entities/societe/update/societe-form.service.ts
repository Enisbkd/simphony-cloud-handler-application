import { Injectable } from '@angular/core';
import { FormControl, FormGroup, Validators } from '@angular/forms';

import { ISociete, NewSociete } from '../societe.model';

/**
 * A partial Type with required key is used as form input.
 */
type PartialWithRequiredKeyOf<T extends { id: unknown }> = Partial<Omit<T, 'id'>> & { id: T['id'] };

/**
 * Type for createFormGroup and resetForm argument.
 * It accepts ISociete for edit and NewSocieteFormGroupInput for create.
 */
type SocieteFormGroupInput = ISociete | PartialWithRequiredKeyOf<NewSociete>;

type SocieteFormDefaults = Pick<NewSociete, 'id'>;

type SocieteFormGroupContent = {
  id: FormControl<ISociete['id'] | NewSociete['id']>;
  nom: FormControl<ISociete['nom']>;
  nomCourt: FormControl<ISociete['nomCourt']>;
};

export type SocieteFormGroup = FormGroup<SocieteFormGroupContent>;

@Injectable({ providedIn: 'root' })
export class SocieteFormService {
  createSocieteFormGroup(societe: SocieteFormGroupInput = { id: null }): SocieteFormGroup {
    const societeRawValue = {
      ...this.getFormDefaults(),
      ...societe,
    };
    return new FormGroup<SocieteFormGroupContent>({
      id: new FormControl(
        { value: societeRawValue.id, disabled: true },
        {
          nonNullable: true,
          validators: [Validators.required],
        },
      ),
      nom: new FormControl(societeRawValue.nom),
      nomCourt: new FormControl(societeRawValue.nomCourt),
    });
  }

  getSociete(form: SocieteFormGroup): ISociete | NewSociete {
    return form.getRawValue() as ISociete | NewSociete;
  }

  resetForm(form: SocieteFormGroup, societe: SocieteFormGroupInput): void {
    const societeRawValue = { ...this.getFormDefaults(), ...societe };
    form.reset(
      {
        ...societeRawValue,
        id: { value: societeRawValue.id, disabled: true },
      } as any /* cast to workaround https://github.com/angular/angular/issues/46458 */,
    );
  }

  private getFormDefaults(): SocieteFormDefaults {
    return {
      id: null,
    };
  }
}
