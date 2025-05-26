import { TestBed } from '@angular/core/testing';

import { sampleWithNewData, sampleWithRequiredData } from '../taxe.test-samples';

import { TaxeFormService } from './taxe-form.service';

describe('Taxe Form Service', () => {
  let service: TaxeFormService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(TaxeFormService);
  });

  describe('Service methods', () => {
    describe('createTaxeFormGroup', () => {
      it('should create a new form with FormControl', () => {
        const formGroup = service.createTaxeFormGroup();

        expect(formGroup.controls).toEqual(
          expect.objectContaining({
            id: expect.any(Object),
            nom: expect.any(Object),
            nomCourt: expect.any(Object),
            vatTaxRate: expect.any(Object),
            classId: expect.any(Object),
            taxType: expect.any(Object),
            etablissementRef: expect.any(Object),
          }),
        );
      });

      it('passing ITaxe should create a new form with FormGroup', () => {
        const formGroup = service.createTaxeFormGroup(sampleWithRequiredData);

        expect(formGroup.controls).toEqual(
          expect.objectContaining({
            id: expect.any(Object),
            nom: expect.any(Object),
            nomCourt: expect.any(Object),
            vatTaxRate: expect.any(Object),
            classId: expect.any(Object),
            taxType: expect.any(Object),
            etablissementRef: expect.any(Object),
          }),
        );
      });
    });

    describe('getTaxe', () => {
      it('should return NewTaxe for default Taxe initial value', () => {
        const formGroup = service.createTaxeFormGroup(sampleWithNewData);

        const taxe = service.getTaxe(formGroup) as any;

        expect(taxe).toMatchObject(sampleWithNewData);
      });

      it('should return NewTaxe for empty Taxe initial value', () => {
        const formGroup = service.createTaxeFormGroup();

        const taxe = service.getTaxe(formGroup) as any;

        expect(taxe).toMatchObject({});
      });

      it('should return ITaxe', () => {
        const formGroup = service.createTaxeFormGroup(sampleWithRequiredData);

        const taxe = service.getTaxe(formGroup) as any;

        expect(taxe).toMatchObject(sampleWithRequiredData);
      });
    });

    describe('resetForm', () => {
      it('passing ITaxe should not enable id FormControl', () => {
        const formGroup = service.createTaxeFormGroup();
        expect(formGroup.controls.id.disabled).toBe(true);

        service.resetForm(formGroup, sampleWithRequiredData);

        expect(formGroup.controls.id.disabled).toBe(true);
      });

      it('passing NewTaxe should disable id FormControl', () => {
        const formGroup = service.createTaxeFormGroup(sampleWithRequiredData);
        expect(formGroup.controls.id.disabled).toBe(true);

        service.resetForm(formGroup, { id: null });

        expect(formGroup.controls.id.disabled).toBe(true);
      });
    });
  });
});
