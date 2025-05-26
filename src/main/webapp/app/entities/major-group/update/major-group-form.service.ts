import { Injectable } from '@angular/core';
import { FormControl, FormGroup, Validators } from '@angular/forms';

import { IMajorGroup, NewMajorGroup } from '../major-group.model';

/**
 * A partial Type with required key is used as form input.
 */
type PartialWithRequiredKeyOf<T extends { id: unknown }> = Partial<Omit<T, 'id'>> & { id: T['id'] };

/**
 * Type for createFormGroup and resetForm argument.
 * It accepts IMajorGroup for edit and NewMajorGroupFormGroupInput for create.
 */
type MajorGroupFormGroupInput = IMajorGroup | PartialWithRequiredKeyOf<NewMajorGroup>;

type MajorGroupFormDefaults = Pick<NewMajorGroup, 'id'>;

type MajorGroupFormGroupContent = {
  id: FormControl<IMajorGroup['id'] | NewMajorGroup['id']>;
  nom: FormControl<IMajorGroup['nom']>;
  nomCourt: FormControl<IMajorGroup['nomCourt']>;
  pointDeVenteRef: FormControl<IMajorGroup['pointDeVenteRef']>;
};

export type MajorGroupFormGroup = FormGroup<MajorGroupFormGroupContent>;

@Injectable({ providedIn: 'root' })
export class MajorGroupFormService {
  createMajorGroupFormGroup(majorGroup: MajorGroupFormGroupInput = { id: null }): MajorGroupFormGroup {
    const majorGroupRawValue = {
      ...this.getFormDefaults(),
      ...majorGroup,
    };
    return new FormGroup<MajorGroupFormGroupContent>({
      id: new FormControl(
        { value: majorGroupRawValue.id, disabled: true },
        {
          nonNullable: true,
          validators: [Validators.required],
        },
      ),
      nom: new FormControl(majorGroupRawValue.nom, {
        validators: [Validators.required],
      }),
      nomCourt: new FormControl(majorGroupRawValue.nomCourt),
      pointDeVenteRef: new FormControl(majorGroupRawValue.pointDeVenteRef, {
        validators: [Validators.required],
      }),
    });
  }

  getMajorGroup(form: MajorGroupFormGroup): IMajorGroup | NewMajorGroup {
    return form.getRawValue() as IMajorGroup | NewMajorGroup;
  }

  resetForm(form: MajorGroupFormGroup, majorGroup: MajorGroupFormGroupInput): void {
    const majorGroupRawValue = { ...this.getFormDefaults(), ...majorGroup };
    form.reset(
      {
        ...majorGroupRawValue,
        id: { value: majorGroupRawValue.id, disabled: true },
      } as any /* cast to workaround https://github.com/angular/angular/issues/46458 */,
    );
  }

  private getFormDefaults(): MajorGroupFormDefaults {
    return {
      id: null,
    };
  }
}
