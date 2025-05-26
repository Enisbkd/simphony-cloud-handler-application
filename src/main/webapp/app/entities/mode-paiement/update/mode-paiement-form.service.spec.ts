import { TestBed } from '@angular/core/testing';

import { sampleWithNewData, sampleWithRequiredData } from '../mode-paiement.test-samples';

import { ModePaiementFormService } from './mode-paiement-form.service';

describe('ModePaiement Form Service', () => {
  let service: ModePaiementFormService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(ModePaiementFormService);
  });

  describe('Service methods', () => {
    describe('createModePaiementFormGroup', () => {
      it('should create a new form with FormControl', () => {
        const formGroup = service.createModePaiementFormGroup();

        expect(formGroup.controls).toEqual(
          expect.objectContaining({
            id: expect.any(Object),
            nom: expect.any(Object),
            nomCourt: expect.any(Object),
            nomMstr: expect.any(Object),
            type: expect.any(Object),
            etablissementRef: expect.any(Object),
          }),
        );
      });

      it('passing IModePaiement should create a new form with FormGroup', () => {
        const formGroup = service.createModePaiementFormGroup(sampleWithRequiredData);

        expect(formGroup.controls).toEqual(
          expect.objectContaining({
            id: expect.any(Object),
            nom: expect.any(Object),
            nomCourt: expect.any(Object),
            nomMstr: expect.any(Object),
            type: expect.any(Object),
            etablissementRef: expect.any(Object),
          }),
        );
      });
    });

    describe('getModePaiement', () => {
      it('should return NewModePaiement for default ModePaiement initial value', () => {
        const formGroup = service.createModePaiementFormGroup(sampleWithNewData);

        const modePaiement = service.getModePaiement(formGroup) as any;

        expect(modePaiement).toMatchObject(sampleWithNewData);
      });

      it('should return NewModePaiement for empty ModePaiement initial value', () => {
        const formGroup = service.createModePaiementFormGroup();

        const modePaiement = service.getModePaiement(formGroup) as any;

        expect(modePaiement).toMatchObject({});
      });

      it('should return IModePaiement', () => {
        const formGroup = service.createModePaiementFormGroup(sampleWithRequiredData);

        const modePaiement = service.getModePaiement(formGroup) as any;

        expect(modePaiement).toMatchObject(sampleWithRequiredData);
      });
    });

    describe('resetForm', () => {
      it('passing IModePaiement should not enable id FormControl', () => {
        const formGroup = service.createModePaiementFormGroup();
        expect(formGroup.controls.id.disabled).toBe(true);

        service.resetForm(formGroup, sampleWithRequiredData);

        expect(formGroup.controls.id.disabled).toBe(true);
      });

      it('passing NewModePaiement should disable id FormControl', () => {
        const formGroup = service.createModePaiementFormGroup(sampleWithRequiredData);
        expect(formGroup.controls.id.disabled).toBe(true);

        service.resetForm(formGroup, { id: null });

        expect(formGroup.controls.id.disabled).toBe(true);
      });
    });
  });
});
