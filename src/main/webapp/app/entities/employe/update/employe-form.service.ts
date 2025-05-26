import { Injectable } from '@angular/core';
import { FormControl, FormGroup, Validators } from '@angular/forms';

import { IEmploye, NewEmploye } from '../employe.model';

/**
 * A partial Type with required key is used as form input.
 */
type PartialWithRequiredKeyOf<T extends { id: unknown }> = Partial<Omit<T, 'id'>> & { id: T['id'] };

/**
 * Type for createFormGroup and resetForm argument.
 * It accepts IEmploye for edit and NewEmployeFormGroupInput for create.
 */
type EmployeFormGroupInput = IEmploye | PartialWithRequiredKeyOf<NewEmploye>;

type EmployeFormDefaults = Pick<NewEmploye, 'id'>;

type EmployeFormGroupContent = {
  id: FormControl<IEmploye['id'] | NewEmploye['id']>;
  num: FormControl<IEmploye['num']>;
  firstName: FormControl<IEmploye['firstName']>;
  lastName: FormControl<IEmploye['lastName']>;
  userName: FormControl<IEmploye['userName']>;
  etablissementRef: FormControl<IEmploye['etablissementRef']>;
};

export type EmployeFormGroup = FormGroup<EmployeFormGroupContent>;

@Injectable({ providedIn: 'root' })
export class EmployeFormService {
  createEmployeFormGroup(employe: EmployeFormGroupInput = { id: null }): EmployeFormGroup {
    const employeRawValue = {
      ...this.getFormDefaults(),
      ...employe,
    };
    return new FormGroup<EmployeFormGroupContent>({
      id: new FormControl(
        { value: employeRawValue.id, disabled: true },
        {
          nonNullable: true,
          validators: [Validators.required],
        },
      ),
      num: new FormControl(employeRawValue.num, {
        validators: [Validators.required],
      }),
      firstName: new FormControl(employeRawValue.firstName),
      lastName: new FormControl(employeRawValue.lastName),
      userName: new FormControl(employeRawValue.userName),
      etablissementRef: new FormControl(employeRawValue.etablissementRef, {
        validators: [Validators.required],
      }),
    });
  }

  getEmploye(form: EmployeFormGroup): IEmploye | NewEmploye {
    return form.getRawValue() as IEmploye | NewEmploye;
  }

  resetForm(form: EmployeFormGroup, employe: EmployeFormGroupInput): void {
    const employeRawValue = { ...this.getFormDefaults(), ...employe };
    form.reset(
      {
        ...employeRawValue,
        id: { value: employeRawValue.id, disabled: true },
      } as any /* cast to workaround https://github.com/angular/angular/issues/46458 */,
    );
  }

  private getFormDefaults(): EmployeFormDefaults {
    return {
      id: null,
    };
  }
}
