import { TestBed } from '@angular/core/testing';

import { sampleWithNewData, sampleWithRequiredData } from '../partie-de-journee.test-samples';

import { PartieDeJourneeFormService } from './partie-de-journee-form.service';

describe('PartieDeJournee Form Service', () => {
  let service: PartieDeJourneeFormService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(PartieDeJourneeFormService);
  });

  describe('Service methods', () => {
    describe('createPartieDeJourneeFormGroup', () => {
      it('should create a new form with FormControl', () => {
        const formGroup = service.createPartieDeJourneeFormGroup();

        expect(formGroup.controls).toEqual(
          expect.objectContaining({
            id: expect.any(Object),
            timeRangeStart: expect.any(Object),
            timeRangeEnd: expect.any(Object),
            nom: expect.any(Object),
          }),
        );
      });

      it('passing IPartieDeJournee should create a new form with FormGroup', () => {
        const formGroup = service.createPartieDeJourneeFormGroup(sampleWithRequiredData);

        expect(formGroup.controls).toEqual(
          expect.objectContaining({
            id: expect.any(Object),
            timeRangeStart: expect.any(Object),
            timeRangeEnd: expect.any(Object),
            nom: expect.any(Object),
          }),
        );
      });
    });

    describe('getPartieDeJournee', () => {
      it('should return NewPartieDeJournee for default PartieDeJournee initial value', () => {
        const formGroup = service.createPartieDeJourneeFormGroup(sampleWithNewData);

        const partieDeJournee = service.getPartieDeJournee(formGroup) as any;

        expect(partieDeJournee).toMatchObject(sampleWithNewData);
      });

      it('should return NewPartieDeJournee for empty PartieDeJournee initial value', () => {
        const formGroup = service.createPartieDeJourneeFormGroup();

        const partieDeJournee = service.getPartieDeJournee(formGroup) as any;

        expect(partieDeJournee).toMatchObject({});
      });

      it('should return IPartieDeJournee', () => {
        const formGroup = service.createPartieDeJourneeFormGroup(sampleWithRequiredData);

        const partieDeJournee = service.getPartieDeJournee(formGroup) as any;

        expect(partieDeJournee).toMatchObject(sampleWithRequiredData);
      });
    });

    describe('resetForm', () => {
      it('passing IPartieDeJournee should not enable id FormControl', () => {
        const formGroup = service.createPartieDeJourneeFormGroup();
        expect(formGroup.controls.id.disabled).toBe(true);

        service.resetForm(formGroup, sampleWithRequiredData);

        expect(formGroup.controls.id.disabled).toBe(true);
      });

      it('passing NewPartieDeJournee should disable id FormControl', () => {
        const formGroup = service.createPartieDeJourneeFormGroup(sampleWithRequiredData);
        expect(formGroup.controls.id.disabled).toBe(true);

        service.resetForm(formGroup, { id: null });

        expect(formGroup.controls.id.disabled).toBe(true);
      });
    });
  });
});
