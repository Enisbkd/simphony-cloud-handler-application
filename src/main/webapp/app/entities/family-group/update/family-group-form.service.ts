import { Injectable } from '@angular/core';
import { FormControl, FormGroup, Validators } from '@angular/forms';

import { IFamilyGroup, NewFamilyGroup } from '../family-group.model';

/**
 * A partial Type with required key is used as form input.
 */
type PartialWithRequiredKeyOf<T extends { id: unknown }> = Partial<Omit<T, 'id'>> & { id: T['id'] };

/**
 * Type for createFormGroup and resetForm argument.
 * It accepts IFamilyGroup for edit and NewFamilyGroupFormGroupInput for create.
 */
type FamilyGroupFormGroupInput = IFamilyGroup | PartialWithRequiredKeyOf<NewFamilyGroup>;

type FamilyGroupFormDefaults = Pick<NewFamilyGroup, 'id'>;

type FamilyGroupFormGroupContent = {
  id: FormControl<IFamilyGroup['id'] | NewFamilyGroup['id']>;
  nom: FormControl<IFamilyGroup['nom']>;
  nomCourt: FormControl<IFamilyGroup['nomCourt']>;
  majorGroupRef: FormControl<IFamilyGroup['majorGroupRef']>;
};

export type FamilyGroupFormGroup = FormGroup<FamilyGroupFormGroupContent>;

@Injectable({ providedIn: 'root' })
export class FamilyGroupFormService {
  createFamilyGroupFormGroup(familyGroup: FamilyGroupFormGroupInput = { id: null }): FamilyGroupFormGroup {
    const familyGroupRawValue = {
      ...this.getFormDefaults(),
      ...familyGroup,
    };
    return new FormGroup<FamilyGroupFormGroupContent>({
      id: new FormControl(
        { value: familyGroupRawValue.id, disabled: true },
        {
          nonNullable: true,
          validators: [Validators.required],
        },
      ),
      nom: new FormControl(familyGroupRawValue.nom),
      nomCourt: new FormControl(familyGroupRawValue.nomCourt, {
        validators: [Validators.required],
      }),
      majorGroupRef: new FormControl(familyGroupRawValue.majorGroupRef),
    });
  }

  getFamilyGroup(form: FamilyGroupFormGroup): IFamilyGroup | NewFamilyGroup {
    return form.getRawValue() as IFamilyGroup | NewFamilyGroup;
  }

  resetForm(form: FamilyGroupFormGroup, familyGroup: FamilyGroupFormGroupInput): void {
    const familyGroupRawValue = { ...this.getFormDefaults(), ...familyGroup };
    form.reset(
      {
        ...familyGroupRawValue,
        id: { value: familyGroupRawValue.id, disabled: true },
      } as any /* cast to workaround https://github.com/angular/angular/issues/46458 */,
    );
  }

  private getFormDefaults(): FamilyGroupFormDefaults {
    return {
      id: null,
    };
  }
}
