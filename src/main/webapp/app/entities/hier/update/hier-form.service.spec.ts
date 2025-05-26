import { TestBed } from '@angular/core/testing';

import { sampleWithNewData, sampleWithRequiredData } from '../hier.test-samples';

import { HierFormService } from './hier-form.service';

describe('Hier Form Service', () => {
  let service: HierFormService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(HierFormService);
  });

  describe('Service methods', () => {
    describe('createHierFormGroup', () => {
      it('should create a new form with FormControl', () => {
        const formGroup = service.createHierFormGroup();

        expect(formGroup.controls).toEqual(
          expect.objectContaining({
            id: expect.any(Object),
            nom: expect.any(Object),
            parentHierId: expect.any(Object),
            unitId: expect.any(Object),
          }),
        );
      });

      it('passing IHier should create a new form with FormGroup', () => {
        const formGroup = service.createHierFormGroup(sampleWithRequiredData);

        expect(formGroup.controls).toEqual(
          expect.objectContaining({
            id: expect.any(Object),
            nom: expect.any(Object),
            parentHierId: expect.any(Object),
            unitId: expect.any(Object),
          }),
        );
      });
    });

    describe('getHier', () => {
      it('should return NewHier for default Hier initial value', () => {
        const formGroup = service.createHierFormGroup(sampleWithNewData);

        const hier = service.getHier(formGroup) as any;

        expect(hier).toMatchObject(sampleWithNewData);
      });

      it('should return NewHier for empty Hier initial value', () => {
        const formGroup = service.createHierFormGroup();

        const hier = service.getHier(formGroup) as any;

        expect(hier).toMatchObject({});
      });

      it('should return IHier', () => {
        const formGroup = service.createHierFormGroup(sampleWithRequiredData);

        const hier = service.getHier(formGroup) as any;

        expect(hier).toMatchObject(sampleWithRequiredData);
      });
    });

    describe('resetForm', () => {
      it('passing IHier should not enable id FormControl', () => {
        const formGroup = service.createHierFormGroup();
        expect(formGroup.controls.id.disabled).toBe(true);

        service.resetForm(formGroup, sampleWithRequiredData);

        expect(formGroup.controls.id.disabled).toBe(true);
      });

      it('passing NewHier should disable id FormControl', () => {
        const formGroup = service.createHierFormGroup(sampleWithRequiredData);
        expect(formGroup.controls.id.disabled).toBe(true);

        service.resetForm(formGroup, { id: null });

        expect(formGroup.controls.id.disabled).toBe(true);
      });
    });
  });
});
