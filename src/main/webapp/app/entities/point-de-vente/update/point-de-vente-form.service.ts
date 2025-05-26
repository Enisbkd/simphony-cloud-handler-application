import { Injectable } from '@angular/core';
import { FormControl, FormGroup, Validators } from '@angular/forms';

import { IPointDeVente, NewPointDeVente } from '../point-de-vente.model';

/**
 * A partial Type with required key is used as form input.
 */
type PartialWithRequiredKeyOf<T extends { id: unknown }> = Partial<Omit<T, 'id'>> & { id: T['id'] };

/**
 * Type for createFormGroup and resetForm argument.
 * It accepts IPointDeVente for edit and NewPointDeVenteFormGroupInput for create.
 */
type PointDeVenteFormGroupInput = IPointDeVente | PartialWithRequiredKeyOf<NewPointDeVente>;

type PointDeVenteFormDefaults = Pick<NewPointDeVente, 'id' | 'estActif'>;

type PointDeVenteFormGroupContent = {
  id: FormControl<IPointDeVente['id'] | NewPointDeVente['id']>;
  nom: FormControl<IPointDeVente['nom']>;
  nomCourt: FormControl<IPointDeVente['nomCourt']>;
  estActif: FormControl<IPointDeVente['estActif']>;
  etablissementRef: FormControl<IPointDeVente['etablissementRef']>;
  hierRef: FormControl<IPointDeVente['hierRef']>;
};

export type PointDeVenteFormGroup = FormGroup<PointDeVenteFormGroupContent>;

@Injectable({ providedIn: 'root' })
export class PointDeVenteFormService {
  createPointDeVenteFormGroup(pointDeVente: PointDeVenteFormGroupInput = { id: null }): PointDeVenteFormGroup {
    const pointDeVenteRawValue = {
      ...this.getFormDefaults(),
      ...pointDeVente,
    };
    return new FormGroup<PointDeVenteFormGroupContent>({
      id: new FormControl(
        { value: pointDeVenteRawValue.id, disabled: true },
        {
          nonNullable: true,
          validators: [Validators.required],
        },
      ),
      nom: new FormControl(pointDeVenteRawValue.nom, {
        validators: [Validators.required],
      }),
      nomCourt: new FormControl(pointDeVenteRawValue.nomCourt),
      estActif: new FormControl(pointDeVenteRawValue.estActif),
      etablissementRef: new FormControl(pointDeVenteRawValue.etablissementRef, {
        validators: [Validators.required],
      }),
      hierRef: new FormControl(pointDeVenteRawValue.hierRef, {
        validators: [Validators.required],
      }),
    });
  }

  getPointDeVente(form: PointDeVenteFormGroup): IPointDeVente | NewPointDeVente {
    return form.getRawValue() as IPointDeVente | NewPointDeVente;
  }

  resetForm(form: PointDeVenteFormGroup, pointDeVente: PointDeVenteFormGroupInput): void {
    const pointDeVenteRawValue = { ...this.getFormDefaults(), ...pointDeVente };
    form.reset(
      {
        ...pointDeVenteRawValue,
        id: { value: pointDeVenteRawValue.id, disabled: true },
      } as any /* cast to workaround https://github.com/angular/angular/issues/46458 */,
    );
  }

  private getFormDefaults(): PointDeVenteFormDefaults {
    return {
      id: null,
      estActif: false,
    };
  }
}
