import { TestBed } from '@angular/core/testing';

import { sampleWithNewData, sampleWithRequiredData } from '../categorie-point-de-vente.test-samples';

import { CategoriePointDeVenteFormService } from './categorie-point-de-vente-form.service';

describe('CategoriePointDeVente Form Service', () => {
  let service: CategoriePointDeVenteFormService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(CategoriePointDeVenteFormService);
  });

  describe('Service methods', () => {
    describe('createCategoriePointDeVenteFormGroup', () => {
      it('should create a new form with FormControl', () => {
        const formGroup = service.createCategoriePointDeVenteFormGroup();

        expect(formGroup.controls).toEqual(
          expect.objectContaining({
            id: expect.any(Object),
            categorie: expect.any(Object),
            etablissementRef: expect.any(Object),
          }),
        );
      });

      it('passing ICategoriePointDeVente should create a new form with FormGroup', () => {
        const formGroup = service.createCategoriePointDeVenteFormGroup(sampleWithRequiredData);

        expect(formGroup.controls).toEqual(
          expect.objectContaining({
            id: expect.any(Object),
            categorie: expect.any(Object),
            etablissementRef: expect.any(Object),
          }),
        );
      });
    });

    describe('getCategoriePointDeVente', () => {
      it('should return NewCategoriePointDeVente for default CategoriePointDeVente initial value', () => {
        const formGroup = service.createCategoriePointDeVenteFormGroup(sampleWithNewData);

        const categoriePointDeVente = service.getCategoriePointDeVente(formGroup) as any;

        expect(categoriePointDeVente).toMatchObject(sampleWithNewData);
      });

      it('should return NewCategoriePointDeVente for empty CategoriePointDeVente initial value', () => {
        const formGroup = service.createCategoriePointDeVenteFormGroup();

        const categoriePointDeVente = service.getCategoriePointDeVente(formGroup) as any;

        expect(categoriePointDeVente).toMatchObject({});
      });

      it('should return ICategoriePointDeVente', () => {
        const formGroup = service.createCategoriePointDeVenteFormGroup(sampleWithRequiredData);

        const categoriePointDeVente = service.getCategoriePointDeVente(formGroup) as any;

        expect(categoriePointDeVente).toMatchObject(sampleWithRequiredData);
      });
    });

    describe('resetForm', () => {
      it('passing ICategoriePointDeVente should not enable id FormControl', () => {
        const formGroup = service.createCategoriePointDeVenteFormGroup();
        expect(formGroup.controls.id.disabled).toBe(true);

        service.resetForm(formGroup, sampleWithRequiredData);

        expect(formGroup.controls.id.disabled).toBe(true);
      });

      it('passing NewCategoriePointDeVente should disable id FormControl', () => {
        const formGroup = service.createCategoriePointDeVenteFormGroup(sampleWithRequiredData);
        expect(formGroup.controls.id.disabled).toBe(true);

        service.resetForm(formGroup, { id: null });

        expect(formGroup.controls.id.disabled).toBe(true);
      });
    });
  });
});
