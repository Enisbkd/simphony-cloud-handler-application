import { TestBed } from '@angular/core/testing';

import { sampleWithNewData, sampleWithRequiredData } from '../element-menu.test-samples';

import { ElementMenuFormService } from './element-menu-form.service';

describe('ElementMenu Form Service', () => {
  let service: ElementMenuFormService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(ElementMenuFormService);
  });

  describe('Service methods', () => {
    describe('createElementMenuFormGroup', () => {
      it('should create a new form with FormControl', () => {
        const formGroup = service.createElementMenuFormGroup();

        expect(formGroup.controls).toEqual(
          expect.objectContaining({
            id: expect.any(Object),
            masterId: expect.any(Object),
            nom: expect.any(Object),
            nomCourt: expect.any(Object),
            familyGroupRef: expect.any(Object),
            prix: expect.any(Object),
            menuRef: expect.any(Object),
          }),
        );
      });

      it('passing IElementMenu should create a new form with FormGroup', () => {
        const formGroup = service.createElementMenuFormGroup(sampleWithRequiredData);

        expect(formGroup.controls).toEqual(
          expect.objectContaining({
            id: expect.any(Object),
            masterId: expect.any(Object),
            nom: expect.any(Object),
            nomCourt: expect.any(Object),
            familyGroupRef: expect.any(Object),
            prix: expect.any(Object),
            menuRef: expect.any(Object),
          }),
        );
      });
    });

    describe('getElementMenu', () => {
      it('should return NewElementMenu for default ElementMenu initial value', () => {
        const formGroup = service.createElementMenuFormGroup(sampleWithNewData);

        const elementMenu = service.getElementMenu(formGroup) as any;

        expect(elementMenu).toMatchObject(sampleWithNewData);
      });

      it('should return NewElementMenu for empty ElementMenu initial value', () => {
        const formGroup = service.createElementMenuFormGroup();

        const elementMenu = service.getElementMenu(formGroup) as any;

        expect(elementMenu).toMatchObject({});
      });

      it('should return IElementMenu', () => {
        const formGroup = service.createElementMenuFormGroup(sampleWithRequiredData);

        const elementMenu = service.getElementMenu(formGroup) as any;

        expect(elementMenu).toMatchObject(sampleWithRequiredData);
      });
    });

    describe('resetForm', () => {
      it('passing IElementMenu should not enable id FormControl', () => {
        const formGroup = service.createElementMenuFormGroup();
        expect(formGroup.controls.id.disabled).toBe(true);

        service.resetForm(formGroup, sampleWithRequiredData);

        expect(formGroup.controls.id.disabled).toBe(true);
      });

      it('passing NewElementMenu should disable id FormControl', () => {
        const formGroup = service.createElementMenuFormGroup(sampleWithRequiredData);
        expect(formGroup.controls.id.disabled).toBe(true);

        service.resetForm(formGroup, { id: null });

        expect(formGroup.controls.id.disabled).toBe(true);
      });
    });
  });
});
