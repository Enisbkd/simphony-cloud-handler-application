import { TestBed } from '@angular/core/testing';

import { sampleWithNewData, sampleWithRequiredData } from '../code-raison.test-samples';

import { CodeRaisonFormService } from './code-raison-form.service';

describe('CodeRaison Form Service', () => {
  let service: CodeRaisonFormService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(CodeRaisonFormService);
  });

  describe('Service methods', () => {
    describe('createCodeRaisonFormGroup', () => {
      it('should create a new form with FormControl', () => {
        const formGroup = service.createCodeRaisonFormGroup();

        expect(formGroup.controls).toEqual(
          expect.objectContaining({
            id: expect.any(Object),
            nomCourt: expect.any(Object),
            nomMstr: expect.any(Object),
            numMstr: expect.any(Object),
            name: expect.any(Object),
            etablissementRef: expect.any(Object),
          }),
        );
      });

      it('passing ICodeRaison should create a new form with FormGroup', () => {
        const formGroup = service.createCodeRaisonFormGroup(sampleWithRequiredData);

        expect(formGroup.controls).toEqual(
          expect.objectContaining({
            id: expect.any(Object),
            nomCourt: expect.any(Object),
            nomMstr: expect.any(Object),
            numMstr: expect.any(Object),
            name: expect.any(Object),
            etablissementRef: expect.any(Object),
          }),
        );
      });
    });

    describe('getCodeRaison', () => {
      it('should return NewCodeRaison for default CodeRaison initial value', () => {
        const formGroup = service.createCodeRaisonFormGroup(sampleWithNewData);

        const codeRaison = service.getCodeRaison(formGroup) as any;

        expect(codeRaison).toMatchObject(sampleWithNewData);
      });

      it('should return NewCodeRaison for empty CodeRaison initial value', () => {
        const formGroup = service.createCodeRaisonFormGroup();

        const codeRaison = service.getCodeRaison(formGroup) as any;

        expect(codeRaison).toMatchObject({});
      });

      it('should return ICodeRaison', () => {
        const formGroup = service.createCodeRaisonFormGroup(sampleWithRequiredData);

        const codeRaison = service.getCodeRaison(formGroup) as any;

        expect(codeRaison).toMatchObject(sampleWithRequiredData);
      });
    });

    describe('resetForm', () => {
      it('passing ICodeRaison should not enable id FormControl', () => {
        const formGroup = service.createCodeRaisonFormGroup();
        expect(formGroup.controls.id.disabled).toBe(true);

        service.resetForm(formGroup, sampleWithRequiredData);

        expect(formGroup.controls.id.disabled).toBe(true);
      });

      it('passing NewCodeRaison should disable id FormControl', () => {
        const formGroup = service.createCodeRaisonFormGroup(sampleWithRequiredData);
        expect(formGroup.controls.id.disabled).toBe(true);

        service.resetForm(formGroup, { id: null });

        expect(formGroup.controls.id.disabled).toBe(true);
      });
    });
  });
});
