import { Injectable } from '@angular/core';
import { FormControl, FormGroup, Validators } from '@angular/forms';

import { IElementMenu, NewElementMenu } from '../element-menu.model';

/**
 * A partial Type with required key is used as form input.
 */
type PartialWithRequiredKeyOf<T extends { id: unknown }> = Partial<Omit<T, 'id'>> & { id: T['id'] };

/**
 * Type for createFormGroup and resetForm argument.
 * It accepts IElementMenu for edit and NewElementMenuFormGroupInput for create.
 */
type ElementMenuFormGroupInput = IElementMenu | PartialWithRequiredKeyOf<NewElementMenu>;

type ElementMenuFormDefaults = Pick<NewElementMenu, 'id'>;

type ElementMenuFormGroupContent = {
  id: FormControl<IElementMenu['id'] | NewElementMenu['id']>;
  masterId: FormControl<IElementMenu['masterId']>;
  nom: FormControl<IElementMenu['nom']>;
  nomCourt: FormControl<IElementMenu['nomCourt']>;
  familyGroupRef: FormControl<IElementMenu['familyGroupRef']>;
  prix: FormControl<IElementMenu['prix']>;
  menuRef: FormControl<IElementMenu['menuRef']>;
};

export type ElementMenuFormGroup = FormGroup<ElementMenuFormGroupContent>;

@Injectable({ providedIn: 'root' })
export class ElementMenuFormService {
  createElementMenuFormGroup(elementMenu: ElementMenuFormGroupInput = { id: null }): ElementMenuFormGroup {
    const elementMenuRawValue = {
      ...this.getFormDefaults(),
      ...elementMenu,
    };
    return new FormGroup<ElementMenuFormGroupContent>({
      id: new FormControl(
        { value: elementMenuRawValue.id, disabled: true },
        {
          nonNullable: true,
          validators: [Validators.required],
        },
      ),
      masterId: new FormControl(elementMenuRawValue.masterId, {
        validators: [Validators.required],
      }),
      nom: new FormControl(elementMenuRawValue.nom, {
        validators: [Validators.required],
      }),
      nomCourt: new FormControl(elementMenuRawValue.nomCourt),
      familyGroupRef: new FormControl(elementMenuRawValue.familyGroupRef, {
        validators: [Validators.required],
      }),
      prix: new FormControl(elementMenuRawValue.prix),
      menuRef: new FormControl(elementMenuRawValue.menuRef, {
        validators: [Validators.required],
      }),
    });
  }

  getElementMenu(form: ElementMenuFormGroup): IElementMenu | NewElementMenu {
    return form.getRawValue() as IElementMenu | NewElementMenu;
  }

  resetForm(form: ElementMenuFormGroup, elementMenu: ElementMenuFormGroupInput): void {
    const elementMenuRawValue = { ...this.getFormDefaults(), ...elementMenu };
    form.reset(
      {
        ...elementMenuRawValue,
        id: { value: elementMenuRawValue.id, disabled: true },
      } as any /* cast to workaround https://github.com/angular/angular/issues/46458 */,
    );
  }

  private getFormDefaults(): ElementMenuFormDefaults {
    return {
      id: null,
    };
  }
}
