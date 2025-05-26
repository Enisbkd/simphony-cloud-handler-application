import { TestBed } from '@angular/core/testing';

import { sampleWithNewData, sampleWithRequiredData } from '../family-group.test-samples';

import { FamilyGroupFormService } from './family-group-form.service';

describe('FamilyGroup Form Service', () => {
  let service: FamilyGroupFormService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(FamilyGroupFormService);
  });

  describe('Service methods', () => {
    describe('createFamilyGroupFormGroup', () => {
      it('should create a new form with FormControl', () => {
        const formGroup = service.createFamilyGroupFormGroup();

        expect(formGroup.controls).toEqual(
          expect.objectContaining({
            id: expect.any(Object),
            nom: expect.any(Object),
            nomCourt: expect.any(Object),
            majorGroupRef: expect.any(Object),
          }),
        );
      });

      it('passing IFamilyGroup should create a new form with FormGroup', () => {
        const formGroup = service.createFamilyGroupFormGroup(sampleWithRequiredData);

        expect(formGroup.controls).toEqual(
          expect.objectContaining({
            id: expect.any(Object),
            nom: expect.any(Object),
            nomCourt: expect.any(Object),
            majorGroupRef: expect.any(Object),
          }),
        );
      });
    });

    describe('getFamilyGroup', () => {
      it('should return NewFamilyGroup for default FamilyGroup initial value', () => {
        const formGroup = service.createFamilyGroupFormGroup(sampleWithNewData);

        const familyGroup = service.getFamilyGroup(formGroup) as any;

        expect(familyGroup).toMatchObject(sampleWithNewData);
      });

      it('should return NewFamilyGroup for empty FamilyGroup initial value', () => {
        const formGroup = service.createFamilyGroupFormGroup();

        const familyGroup = service.getFamilyGroup(formGroup) as any;

        expect(familyGroup).toMatchObject({});
      });

      it('should return IFamilyGroup', () => {
        const formGroup = service.createFamilyGroupFormGroup(sampleWithRequiredData);

        const familyGroup = service.getFamilyGroup(formGroup) as any;

        expect(familyGroup).toMatchObject(sampleWithRequiredData);
      });
    });

    describe('resetForm', () => {
      it('passing IFamilyGroup should not enable id FormControl', () => {
        const formGroup = service.createFamilyGroupFormGroup();
        expect(formGroup.controls.id.disabled).toBe(true);

        service.resetForm(formGroup, sampleWithRequiredData);

        expect(formGroup.controls.id.disabled).toBe(true);
      });

      it('passing NewFamilyGroup should disable id FormControl', () => {
        const formGroup = service.createFamilyGroupFormGroup(sampleWithRequiredData);
        expect(formGroup.controls.id.disabled).toBe(true);

        service.resetForm(formGroup, { id: null });

        expect(formGroup.controls.id.disabled).toBe(true);
      });
    });
  });
});
