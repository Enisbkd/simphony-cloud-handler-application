import { Injectable } from '@angular/core';
import { FormControl, FormGroup, Validators } from '@angular/forms';

import { ICodeRaison, NewCodeRaison } from '../code-raison.model';

/**
 * A partial Type with required key is used as form input.
 */
type PartialWithRequiredKeyOf<T extends { id: unknown }> = Partial<Omit<T, 'id'>> & { id: T['id'] };

/**
 * Type for createFormGroup and resetForm argument.
 * It accepts ICodeRaison for edit and NewCodeRaisonFormGroupInput for create.
 */
type CodeRaisonFormGroupInput = ICodeRaison | PartialWithRequiredKeyOf<NewCodeRaison>;

type CodeRaisonFormDefaults = Pick<NewCodeRaison, 'id'>;

type CodeRaisonFormGroupContent = {
  id: FormControl<ICodeRaison['id'] | NewCodeRaison['id']>;
  nomCourt: FormControl<ICodeRaison['nomCourt']>;
  nomMstr: FormControl<ICodeRaison['nomMstr']>;
  numMstr: FormControl<ICodeRaison['numMstr']>;
  name: FormControl<ICodeRaison['name']>;
  etablissementRef: FormControl<ICodeRaison['etablissementRef']>;
};

export type CodeRaisonFormGroup = FormGroup<CodeRaisonFormGroupContent>;

@Injectable({ providedIn: 'root' })
export class CodeRaisonFormService {
  createCodeRaisonFormGroup(codeRaison: CodeRaisonFormGroupInput = { id: null }): CodeRaisonFormGroup {
    const codeRaisonRawValue = {
      ...this.getFormDefaults(),
      ...codeRaison,
    };
    return new FormGroup<CodeRaisonFormGroupContent>({
      id: new FormControl(
        { value: codeRaisonRawValue.id, disabled: true },
        {
          nonNullable: true,
          validators: [Validators.required],
        },
      ),
      nomCourt: new FormControl(codeRaisonRawValue.nomCourt, {
        validators: [Validators.required],
      }),
      nomMstr: new FormControl(codeRaisonRawValue.nomMstr),
      numMstr: new FormControl(codeRaisonRawValue.numMstr),
      name: new FormControl(codeRaisonRawValue.name),
      etablissementRef: new FormControl(codeRaisonRawValue.etablissementRef, {
        validators: [Validators.required],
      }),
    });
  }

  getCodeRaison(form: CodeRaisonFormGroup): ICodeRaison | NewCodeRaison {
    return form.getRawValue() as ICodeRaison | NewCodeRaison;
  }

  resetForm(form: CodeRaisonFormGroup, codeRaison: CodeRaisonFormGroupInput): void {
    const codeRaisonRawValue = { ...this.getFormDefaults(), ...codeRaison };
    form.reset(
      {
        ...codeRaisonRawValue,
        id: { value: codeRaisonRawValue.id, disabled: true },
      } as any /* cast to workaround https://github.com/angular/angular/issues/46458 */,
    );
  }

  private getFormDefaults(): CodeRaisonFormDefaults {
    return {
      id: null,
    };
  }
}
