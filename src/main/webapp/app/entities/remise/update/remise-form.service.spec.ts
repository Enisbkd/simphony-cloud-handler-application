import { TestBed } from '@angular/core/testing';

import { sampleWithNewData, sampleWithRequiredData } from '../remise.test-samples';

import { RemiseFormService } from './remise-form.service';

describe('Remise Form Service', () => {
  let service: RemiseFormService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(RemiseFormService);
  });

  describe('Service methods', () => {
    describe('createRemiseFormGroup', () => {
      it('should create a new form with FormControl', () => {
        const formGroup = service.createRemiseFormGroup();

        expect(formGroup.controls).toEqual(
          expect.objectContaining({
            id: expect.any(Object),
            nom: expect.any(Object),
            nomCourt: expect.any(Object),
            nomMstr: expect.any(Object),
            typeValue: expect.any(Object),
            value: expect.any(Object),
            pointDeVenteRef: expect.any(Object),
          }),
        );
      });

      it('passing IRemise should create a new form with FormGroup', () => {
        const formGroup = service.createRemiseFormGroup(sampleWithRequiredData);

        expect(formGroup.controls).toEqual(
          expect.objectContaining({
            id: expect.any(Object),
            nom: expect.any(Object),
            nomCourt: expect.any(Object),
            nomMstr: expect.any(Object),
            typeValue: expect.any(Object),
            value: expect.any(Object),
            pointDeVenteRef: expect.any(Object),
          }),
        );
      });
    });

    describe('getRemise', () => {
      it('should return NewRemise for default Remise initial value', () => {
        const formGroup = service.createRemiseFormGroup(sampleWithNewData);

        const remise = service.getRemise(formGroup) as any;

        expect(remise).toMatchObject(sampleWithNewData);
      });

      it('should return NewRemise for empty Remise initial value', () => {
        const formGroup = service.createRemiseFormGroup();

        const remise = service.getRemise(formGroup) as any;

        expect(remise).toMatchObject({});
      });

      it('should return IRemise', () => {
        const formGroup = service.createRemiseFormGroup(sampleWithRequiredData);

        const remise = service.getRemise(formGroup) as any;

        expect(remise).toMatchObject(sampleWithRequiredData);
      });
    });

    describe('resetForm', () => {
      it('passing IRemise should not enable id FormControl', () => {
        const formGroup = service.createRemiseFormGroup();
        expect(formGroup.controls.id.disabled).toBe(true);

        service.resetForm(formGroup, sampleWithRequiredData);

        expect(formGroup.controls.id.disabled).toBe(true);
      });

      it('passing NewRemise should disable id FormControl', () => {
        const formGroup = service.createRemiseFormGroup(sampleWithRequiredData);
        expect(formGroup.controls.id.disabled).toBe(true);

        service.resetForm(formGroup, { id: null });

        expect(formGroup.controls.id.disabled).toBe(true);
      });
    });
  });
});
