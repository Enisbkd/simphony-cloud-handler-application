import { Injectable } from '@angular/core';
import { FormControl, FormGroup, Validators } from '@angular/forms';

import { ICategoriePointDeVente, NewCategoriePointDeVente } from '../categorie-point-de-vente.model';

/**
 * A partial Type with required key is used as form input.
 */
type PartialWithRequiredKeyOf<T extends { id: unknown }> = Partial<Omit<T, 'id'>> & { id: T['id'] };

/**
 * Type for createFormGroup and resetForm argument.
 * It accepts ICategoriePointDeVente for edit and NewCategoriePointDeVenteFormGroupInput for create.
 */
type CategoriePointDeVenteFormGroupInput = ICategoriePointDeVente | PartialWithRequiredKeyOf<NewCategoriePointDeVente>;

type CategoriePointDeVenteFormDefaults = Pick<NewCategoriePointDeVente, 'id'>;

type CategoriePointDeVenteFormGroupContent = {
  id: FormControl<ICategoriePointDeVente['id'] | NewCategoriePointDeVente['id']>;
  categorie: FormControl<ICategoriePointDeVente['categorie']>;
  etablissementRef: FormControl<ICategoriePointDeVente['etablissementRef']>;
};

export type CategoriePointDeVenteFormGroup = FormGroup<CategoriePointDeVenteFormGroupContent>;

@Injectable({ providedIn: 'root' })
export class CategoriePointDeVenteFormService {
  createCategoriePointDeVenteFormGroup(
    categoriePointDeVente: CategoriePointDeVenteFormGroupInput = { id: null },
  ): CategoriePointDeVenteFormGroup {
    const categoriePointDeVenteRawValue = {
      ...this.getFormDefaults(),
      ...categoriePointDeVente,
    };
    return new FormGroup<CategoriePointDeVenteFormGroupContent>({
      id: new FormControl(
        { value: categoriePointDeVenteRawValue.id, disabled: true },
        {
          nonNullable: true,
          validators: [Validators.required],
        },
      ),
      categorie: new FormControl(categoriePointDeVenteRawValue.categorie),
      etablissementRef: new FormControl(categoriePointDeVenteRawValue.etablissementRef),
    });
  }

  getCategoriePointDeVente(form: CategoriePointDeVenteFormGroup): ICategoriePointDeVente | NewCategoriePointDeVente {
    return form.getRawValue() as ICategoriePointDeVente | NewCategoriePointDeVente;
  }

  resetForm(form: CategoriePointDeVenteFormGroup, categoriePointDeVente: CategoriePointDeVenteFormGroupInput): void {
    const categoriePointDeVenteRawValue = { ...this.getFormDefaults(), ...categoriePointDeVente };
    form.reset(
      {
        ...categoriePointDeVenteRawValue,
        id: { value: categoriePointDeVenteRawValue.id, disabled: true },
      } as any /* cast to workaround https://github.com/angular/angular/issues/46458 */,
    );
  }

  private getFormDefaults(): CategoriePointDeVenteFormDefaults {
    return {
      id: null,
    };
  }
}
