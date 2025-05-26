import { TestBed } from '@angular/core/testing';

import { sampleWithNewData, sampleWithRequiredData } from '../major-group.test-samples';

import { MajorGroupFormService } from './major-group-form.service';

describe('MajorGroup Form Service', () => {
  let service: MajorGroupFormService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(MajorGroupFormService);
  });

  describe('Service methods', () => {
    describe('createMajorGroupFormGroup', () => {
      it('should create a new form with FormControl', () => {
        const formGroup = service.createMajorGroupFormGroup();

        expect(formGroup.controls).toEqual(
          expect.objectContaining({
            id: expect.any(Object),
            nom: expect.any(Object),
            nomCourt: expect.any(Object),
            pointDeVenteRef: expect.any(Object),
          }),
        );
      });

      it('passing IMajorGroup should create a new form with FormGroup', () => {
        const formGroup = service.createMajorGroupFormGroup(sampleWithRequiredData);

        expect(formGroup.controls).toEqual(
          expect.objectContaining({
            id: expect.any(Object),
            nom: expect.any(Object),
            nomCourt: expect.any(Object),
            pointDeVenteRef: expect.any(Object),
          }),
        );
      });
    });

    describe('getMajorGroup', () => {
      it('should return NewMajorGroup for default MajorGroup initial value', () => {
        const formGroup = service.createMajorGroupFormGroup(sampleWithNewData);

        const majorGroup = service.getMajorGroup(formGroup) as any;

        expect(majorGroup).toMatchObject(sampleWithNewData);
      });

      it('should return NewMajorGroup for empty MajorGroup initial value', () => {
        const formGroup = service.createMajorGroupFormGroup();

        const majorGroup = service.getMajorGroup(formGroup) as any;

        expect(majorGroup).toMatchObject({});
      });

      it('should return IMajorGroup', () => {
        const formGroup = service.createMajorGroupFormGroup(sampleWithRequiredData);

        const majorGroup = service.getMajorGroup(formGroup) as any;

        expect(majorGroup).toMatchObject(sampleWithRequiredData);
      });
    });

    describe('resetForm', () => {
      it('passing IMajorGroup should not enable id FormControl', () => {
        const formGroup = service.createMajorGroupFormGroup();
        expect(formGroup.controls.id.disabled).toBe(true);

        service.resetForm(formGroup, sampleWithRequiredData);

        expect(formGroup.controls.id.disabled).toBe(true);
      });

      it('passing NewMajorGroup should disable id FormControl', () => {
        const formGroup = service.createMajorGroupFormGroup(sampleWithRequiredData);
        expect(formGroup.controls.id.disabled).toBe(true);

        service.resetForm(formGroup, { id: null });

        expect(formGroup.controls.id.disabled).toBe(true);
      });
    });
  });
});
