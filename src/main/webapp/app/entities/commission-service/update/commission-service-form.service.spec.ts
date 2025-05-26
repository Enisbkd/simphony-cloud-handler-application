import { TestBed } from '@angular/core/testing';

import { sampleWithNewData, sampleWithRequiredData } from '../commission-service.test-samples';

import { CommissionServiceFormService } from './commission-service-form.service';

describe('CommissionService Form Service', () => {
  let service: CommissionServiceFormService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(CommissionServiceFormService);
  });

  describe('Service methods', () => {
    describe('createCommissionServiceFormGroup', () => {
      it('should create a new form with FormControl', () => {
        const formGroup = service.createCommissionServiceFormGroup();

        expect(formGroup.controls).toEqual(
          expect.objectContaining({
            id: expect.any(Object),
            nom: expect.any(Object),
            nomCourt: expect.any(Object),
            typeValue: expect.any(Object),
            value: expect.any(Object),
            etablissementRef: expect.any(Object),
          }),
        );
      });

      it('passing ICommissionService should create a new form with FormGroup', () => {
        const formGroup = service.createCommissionServiceFormGroup(sampleWithRequiredData);

        expect(formGroup.controls).toEqual(
          expect.objectContaining({
            id: expect.any(Object),
            nom: expect.any(Object),
            nomCourt: expect.any(Object),
            typeValue: expect.any(Object),
            value: expect.any(Object),
            etablissementRef: expect.any(Object),
          }),
        );
      });
    });

    describe('getCommissionService', () => {
      it('should return NewCommissionService for default CommissionService initial value', () => {
        const formGroup = service.createCommissionServiceFormGroup(sampleWithNewData);

        const commissionService = service.getCommissionService(formGroup) as any;

        expect(commissionService).toMatchObject(sampleWithNewData);
      });

      it('should return NewCommissionService for empty CommissionService initial value', () => {
        const formGroup = service.createCommissionServiceFormGroup();

        const commissionService = service.getCommissionService(formGroup) as any;

        expect(commissionService).toMatchObject({});
      });

      it('should return ICommissionService', () => {
        const formGroup = service.createCommissionServiceFormGroup(sampleWithRequiredData);

        const commissionService = service.getCommissionService(formGroup) as any;

        expect(commissionService).toMatchObject(sampleWithRequiredData);
      });
    });

    describe('resetForm', () => {
      it('passing ICommissionService should not enable id FormControl', () => {
        const formGroup = service.createCommissionServiceFormGroup();
        expect(formGroup.controls.id.disabled).toBe(true);

        service.resetForm(formGroup, sampleWithRequiredData);

        expect(formGroup.controls.id.disabled).toBe(true);
      });

      it('passing NewCommissionService should disable id FormControl', () => {
        const formGroup = service.createCommissionServiceFormGroup(sampleWithRequiredData);
        expect(formGroup.controls.id.disabled).toBe(true);

        service.resetForm(formGroup, { id: null });

        expect(formGroup.controls.id.disabled).toBe(true);
      });
    });
  });
});
