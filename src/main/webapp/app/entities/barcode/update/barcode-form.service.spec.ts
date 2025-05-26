import { TestBed } from '@angular/core/testing';

import { sampleWithNewData, sampleWithRequiredData } from '../barcode.test-samples';

import { BarcodeFormService } from './barcode-form.service';

describe('Barcode Form Service', () => {
  let service: BarcodeFormService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(BarcodeFormService);
  });

  describe('Service methods', () => {
    describe('createBarcodeFormGroup', () => {
      it('should create a new form with FormControl', () => {
        const formGroup = service.createBarcodeFormGroup();

        expect(formGroup.controls).toEqual(
          expect.objectContaining({
            id: expect.any(Object),
            num: expect.any(Object),
            barcode: expect.any(Object),
            prix: expect.any(Object),
            coutPreparation: expect.any(Object),
            defNumSequence: expect.any(Object),
            prixNumSequence: expect.any(Object),
            pointDeVenteRef: expect.any(Object),
            elementMenuRef: expect.any(Object),
          }),
        );
      });

      it('passing IBarcode should create a new form with FormGroup', () => {
        const formGroup = service.createBarcodeFormGroup(sampleWithRequiredData);

        expect(formGroup.controls).toEqual(
          expect.objectContaining({
            id: expect.any(Object),
            num: expect.any(Object),
            barcode: expect.any(Object),
            prix: expect.any(Object),
            coutPreparation: expect.any(Object),
            defNumSequence: expect.any(Object),
            prixNumSequence: expect.any(Object),
            pointDeVenteRef: expect.any(Object),
            elementMenuRef: expect.any(Object),
          }),
        );
      });
    });

    describe('getBarcode', () => {
      it('should return NewBarcode for default Barcode initial value', () => {
        const formGroup = service.createBarcodeFormGroup(sampleWithNewData);

        const barcode = service.getBarcode(formGroup) as any;

        expect(barcode).toMatchObject(sampleWithNewData);
      });

      it('should return NewBarcode for empty Barcode initial value', () => {
        const formGroup = service.createBarcodeFormGroup();

        const barcode = service.getBarcode(formGroup) as any;

        expect(barcode).toMatchObject({});
      });

      it('should return IBarcode', () => {
        const formGroup = service.createBarcodeFormGroup(sampleWithRequiredData);

        const barcode = service.getBarcode(formGroup) as any;

        expect(barcode).toMatchObject(sampleWithRequiredData);
      });
    });

    describe('resetForm', () => {
      it('passing IBarcode should not enable id FormControl', () => {
        const formGroup = service.createBarcodeFormGroup();
        expect(formGroup.controls.id.disabled).toBe(true);

        service.resetForm(formGroup, sampleWithRequiredData);

        expect(formGroup.controls.id.disabled).toBe(true);
      });

      it('passing NewBarcode should disable id FormControl', () => {
        const formGroup = service.createBarcodeFormGroup(sampleWithRequiredData);
        expect(formGroup.controls.id.disabled).toBe(true);

        service.resetForm(formGroup, { id: null });

        expect(formGroup.controls.id.disabled).toBe(true);
      });
    });
  });
});
