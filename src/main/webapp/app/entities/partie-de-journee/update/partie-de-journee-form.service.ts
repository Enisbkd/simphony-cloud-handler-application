import { Injectable } from '@angular/core';
import { FormControl, FormGroup, Validators } from '@angular/forms';

import { IPartieDeJournee, NewPartieDeJournee } from '../partie-de-journee.model';

/**
 * A partial Type with required key is used as form input.
 */
type PartialWithRequiredKeyOf<T extends { id: unknown }> = Partial<Omit<T, 'id'>> & { id: T['id'] };

/**
 * Type for createFormGroup and resetForm argument.
 * It accepts IPartieDeJournee for edit and NewPartieDeJourneeFormGroupInput for create.
 */
type PartieDeJourneeFormGroupInput = IPartieDeJournee | PartialWithRequiredKeyOf<NewPartieDeJournee>;

type PartieDeJourneeFormDefaults = Pick<NewPartieDeJournee, 'id'>;

type PartieDeJourneeFormGroupContent = {
  id: FormControl<IPartieDeJournee['id'] | NewPartieDeJournee['id']>;
  timeRangeStart: FormControl<IPartieDeJournee['timeRangeStart']>;
  timeRangeEnd: FormControl<IPartieDeJournee['timeRangeEnd']>;
  nom: FormControl<IPartieDeJournee['nom']>;
};

export type PartieDeJourneeFormGroup = FormGroup<PartieDeJourneeFormGroupContent>;

@Injectable({ providedIn: 'root' })
export class PartieDeJourneeFormService {
  createPartieDeJourneeFormGroup(partieDeJournee: PartieDeJourneeFormGroupInput = { id: null }): PartieDeJourneeFormGroup {
    const partieDeJourneeRawValue = {
      ...this.getFormDefaults(),
      ...partieDeJournee,
    };
    return new FormGroup<PartieDeJourneeFormGroupContent>({
      id: new FormControl(
        { value: partieDeJourneeRawValue.id, disabled: true },
        {
          nonNullable: true,
          validators: [Validators.required],
        },
      ),
      timeRangeStart: new FormControl(partieDeJourneeRawValue.timeRangeStart),
      timeRangeEnd: new FormControl(partieDeJourneeRawValue.timeRangeEnd),
      nom: new FormControl(partieDeJourneeRawValue.nom, {
        validators: [Validators.required],
      }),
    });
  }

  getPartieDeJournee(form: PartieDeJourneeFormGroup): IPartieDeJournee | NewPartieDeJournee {
    return form.getRawValue() as IPartieDeJournee | NewPartieDeJournee;
  }

  resetForm(form: PartieDeJourneeFormGroup, partieDeJournee: PartieDeJourneeFormGroupInput): void {
    const partieDeJourneeRawValue = { ...this.getFormDefaults(), ...partieDeJournee };
    form.reset(
      {
        ...partieDeJourneeRawValue,
        id: { value: partieDeJourneeRawValue.id, disabled: true },
      } as any /* cast to workaround https://github.com/angular/angular/issues/46458 */,
    );
  }

  private getFormDefaults(): PartieDeJourneeFormDefaults {
    return {
      id: null,
    };
  }
}
