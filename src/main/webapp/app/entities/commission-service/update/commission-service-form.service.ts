import { Injectable } from '@angular/core';
import { FormControl, FormGroup, Validators } from '@angular/forms';

import { ICommissionService, NewCommissionService } from '../commission-service.model';

/**
 * A partial Type with required key is used as form input.
 */
type PartialWithRequiredKeyOf<T extends { id: unknown }> = Partial<Omit<T, 'id'>> & { id: T['id'] };

/**
 * Type for createFormGroup and resetForm argument.
 * It accepts ICommissionService for edit and NewCommissionServiceFormGroupInput for create.
 */
type CommissionServiceFormGroupInput = ICommissionService | PartialWithRequiredKeyOf<NewCommissionService>;

type CommissionServiceFormDefaults = Pick<NewCommissionService, 'id'>;

type CommissionServiceFormGroupContent = {
  id: FormControl<ICommissionService['id'] | NewCommissionService['id']>;
  nom: FormControl<ICommissionService['nom']>;
  nomCourt: FormControl<ICommissionService['nomCourt']>;
  typeValue: FormControl<ICommissionService['typeValue']>;
  value: FormControl<ICommissionService['value']>;
  etablissementRef: FormControl<ICommissionService['etablissementRef']>;
};

export type CommissionServiceFormGroup = FormGroup<CommissionServiceFormGroupContent>;

@Injectable({ providedIn: 'root' })
export class CommissionServiceFormService {
  createCommissionServiceFormGroup(commissionService: CommissionServiceFormGroupInput = { id: null }): CommissionServiceFormGroup {
    const commissionServiceRawValue = {
      ...this.getFormDefaults(),
      ...commissionService,
    };
    return new FormGroup<CommissionServiceFormGroupContent>({
      id: new FormControl(
        { value: commissionServiceRawValue.id, disabled: true },
        {
          nonNullable: true,
          validators: [Validators.required],
        },
      ),
      nom: new FormControl(commissionServiceRawValue.nom),
      nomCourt: new FormControl(commissionServiceRawValue.nomCourt),
      typeValue: new FormControl(commissionServiceRawValue.typeValue),
      value: new FormControl(commissionServiceRawValue.value),
      etablissementRef: new FormControl(commissionServiceRawValue.etablissementRef, {
        validators: [Validators.required],
      }),
    });
  }

  getCommissionService(form: CommissionServiceFormGroup): ICommissionService | NewCommissionService {
    return form.getRawValue() as ICommissionService | NewCommissionService;
  }

  resetForm(form: CommissionServiceFormGroup, commissionService: CommissionServiceFormGroupInput): void {
    const commissionServiceRawValue = { ...this.getFormDefaults(), ...commissionService };
    form.reset(
      {
        ...commissionServiceRawValue,
        id: { value: commissionServiceRawValue.id, disabled: true },
      } as any /* cast to workaround https://github.com/angular/angular/issues/46458 */,
    );
  }

  private getFormDefaults(): CommissionServiceFormDefaults {
    return {
      id: null,
    };
  }
}
