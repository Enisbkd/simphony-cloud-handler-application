import { Injectable } from '@angular/core';
import { FormControl, FormGroup, Validators } from '@angular/forms';

import { IHier, NewHier } from '../hier.model';

/**
 * A partial Type with required key is used as form input.
 */
type PartialWithRequiredKeyOf<T extends { id: unknown }> = Partial<Omit<T, 'id'>> & { id: T['id'] };

/**
 * Type for createFormGroup and resetForm argument.
 * It accepts IHier for edit and NewHierFormGroupInput for create.
 */
type HierFormGroupInput = IHier | PartialWithRequiredKeyOf<NewHier>;

type HierFormDefaults = Pick<NewHier, 'id'>;

type HierFormGroupContent = {
  id: FormControl<IHier['id'] | NewHier['id']>;
  nom: FormControl<IHier['nom']>;
  parentHierId: FormControl<IHier['parentHierId']>;
  unitId: FormControl<IHier['unitId']>;
};

export type HierFormGroup = FormGroup<HierFormGroupContent>;

@Injectable({ providedIn: 'root' })
export class HierFormService {
  createHierFormGroup(hier: HierFormGroupInput = { id: null }): HierFormGroup {
    const hierRawValue = {
      ...this.getFormDefaults(),
      ...hier,
    };
    return new FormGroup<HierFormGroupContent>({
      id: new FormControl(
        { value: hierRawValue.id, disabled: true },
        {
          nonNullable: true,
          validators: [Validators.required],
        },
      ),
      nom: new FormControl(hierRawValue.nom, {
        validators: [Validators.required],
      }),
      parentHierId: new FormControl(hierRawValue.parentHierId),
      unitId: new FormControl(hierRawValue.unitId),
    });
  }

  getHier(form: HierFormGroup): IHier | NewHier {
    return form.getRawValue() as IHier | NewHier;
  }

  resetForm(form: HierFormGroup, hier: HierFormGroupInput): void {
    const hierRawValue = { ...this.getFormDefaults(), ...hier };
    form.reset(
      {
        ...hierRawValue,
        id: { value: hierRawValue.id, disabled: true },
      } as any /* cast to workaround https://github.com/angular/angular/issues/46458 */,
    );
  }

  private getFormDefaults(): HierFormDefaults {
    return {
      id: null,
    };
  }
}
