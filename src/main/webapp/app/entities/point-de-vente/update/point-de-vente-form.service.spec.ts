import { TestBed } from '@angular/core/testing';

import { sampleWithNewData, sampleWithRequiredData } from '../point-de-vente.test-samples';

import { PointDeVenteFormService } from './point-de-vente-form.service';

describe('PointDeVente Form Service', () => {
  let service: PointDeVenteFormService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(PointDeVenteFormService);
  });

  describe('Service methods', () => {
    describe('createPointDeVenteFormGroup', () => {
      it('should create a new form with FormControl', () => {
        const formGroup = service.createPointDeVenteFormGroup();

        expect(formGroup.controls).toEqual(
          expect.objectContaining({
            id: expect.any(Object),
            nom: expect.any(Object),
            nomCourt: expect.any(Object),
            estActif: expect.any(Object),
            etablissementRef: expect.any(Object),
            hierRef: expect.any(Object),
          }),
        );
      });

      it('passing IPointDeVente should create a new form with FormGroup', () => {
        const formGroup = service.createPointDeVenteFormGroup(sampleWithRequiredData);

        expect(formGroup.controls).toEqual(
          expect.objectContaining({
            id: expect.any(Object),
            nom: expect.any(Object),
            nomCourt: expect.any(Object),
            estActif: expect.any(Object),
            etablissementRef: expect.any(Object),
            hierRef: expect.any(Object),
          }),
        );
      });
    });

    describe('getPointDeVente', () => {
      it('should return NewPointDeVente for default PointDeVente initial value', () => {
        const formGroup = service.createPointDeVenteFormGroup(sampleWithNewData);

        const pointDeVente = service.getPointDeVente(formGroup) as any;

        expect(pointDeVente).toMatchObject(sampleWithNewData);
      });

      it('should return NewPointDeVente for empty PointDeVente initial value', () => {
        const formGroup = service.createPointDeVenteFormGroup();

        const pointDeVente = service.getPointDeVente(formGroup) as any;

        expect(pointDeVente).toMatchObject({});
      });

      it('should return IPointDeVente', () => {
        const formGroup = service.createPointDeVenteFormGroup(sampleWithRequiredData);

        const pointDeVente = service.getPointDeVente(formGroup) as any;

        expect(pointDeVente).toMatchObject(sampleWithRequiredData);
      });
    });

    describe('resetForm', () => {
      it('passing IPointDeVente should not enable id FormControl', () => {
        const formGroup = service.createPointDeVenteFormGroup();
        expect(formGroup.controls.id.disabled).toBe(true);

        service.resetForm(formGroup, sampleWithRequiredData);

        expect(formGroup.controls.id.disabled).toBe(true);
      });

      it('passing NewPointDeVente should disable id FormControl', () => {
        const formGroup = service.createPointDeVenteFormGroup(sampleWithRequiredData);
        expect(formGroup.controls.id.disabled).toBe(true);

        service.resetForm(formGroup, { id: null });

        expect(formGroup.controls.id.disabled).toBe(true);
      });
    });
  });
});
