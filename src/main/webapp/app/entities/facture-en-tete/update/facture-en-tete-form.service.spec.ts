import { TestBed } from '@angular/core/testing';

import { sampleWithNewData, sampleWithRequiredData } from '../facture-en-tete.test-samples';

import { FactureEnTeteFormService } from './facture-en-tete-form.service';

describe('FactureEnTete Form Service', () => {
  let service: FactureEnTeteFormService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(FactureEnTeteFormService);
  });

  describe('Service methods', () => {
    describe('createFactureEnTeteFormGroup', () => {
      it('should create a new form with FormControl', () => {
        const formGroup = service.createFactureEnTeteFormGroup();

        expect(formGroup.controls).toEqual(
          expect.objectContaining({
            id: expect.any(Object),
            num: expect.any(Object),
            factureRef: expect.any(Object),
            ouvertureDateTime: expect.any(Object),
            fermetureDateTime: expect.any(Object),
            estAnnule: expect.any(Object),
            nbrePax: expect.any(Object),
            numTable: expect.any(Object),
            taxeMontantTotal: expect.any(Object),
            sousTotal: expect.any(Object),
            factureTotal: expect.any(Object),
            commissionTotal: expect.any(Object),
            tipTotal: expect.any(Object),
            remiseTotal: expect.any(Object),
            erreursCorrigeesTotal: expect.any(Object),
            retourTotal: expect.any(Object),
            xferToFactureEnTeteRef: expect.any(Object),
            xferStatus: expect.any(Object),
            categoriePointDeVenteRef: expect.any(Object),
            pointDeVenteRef: expect.any(Object),
          }),
        );
      });

      it('passing IFactureEnTete should create a new form with FormGroup', () => {
        const formGroup = service.createFactureEnTeteFormGroup(sampleWithRequiredData);

        expect(formGroup.controls).toEqual(
          expect.objectContaining({
            id: expect.any(Object),
            num: expect.any(Object),
            factureRef: expect.any(Object),
            ouvertureDateTime: expect.any(Object),
            fermetureDateTime: expect.any(Object),
            estAnnule: expect.any(Object),
            nbrePax: expect.any(Object),
            numTable: expect.any(Object),
            taxeMontantTotal: expect.any(Object),
            sousTotal: expect.any(Object),
            factureTotal: expect.any(Object),
            commissionTotal: expect.any(Object),
            tipTotal: expect.any(Object),
            remiseTotal: expect.any(Object),
            erreursCorrigeesTotal: expect.any(Object),
            retourTotal: expect.any(Object),
            xferToFactureEnTeteRef: expect.any(Object),
            xferStatus: expect.any(Object),
            categoriePointDeVenteRef: expect.any(Object),
            pointDeVenteRef: expect.any(Object),
          }),
        );
      });
    });

    describe('getFactureEnTete', () => {
      it('should return NewFactureEnTete for default FactureEnTete initial value', () => {
        const formGroup = service.createFactureEnTeteFormGroup(sampleWithNewData);

        const factureEnTete = service.getFactureEnTete(formGroup) as any;

        expect(factureEnTete).toMatchObject(sampleWithNewData);
      });

      it('should return NewFactureEnTete for empty FactureEnTete initial value', () => {
        const formGroup = service.createFactureEnTeteFormGroup();

        const factureEnTete = service.getFactureEnTete(formGroup) as any;

        expect(factureEnTete).toMatchObject({});
      });

      it('should return IFactureEnTete', () => {
        const formGroup = service.createFactureEnTeteFormGroup(sampleWithRequiredData);

        const factureEnTete = service.getFactureEnTete(formGroup) as any;

        expect(factureEnTete).toMatchObject(sampleWithRequiredData);
      });
    });

    describe('resetForm', () => {
      it('passing IFactureEnTete should not enable id FormControl', () => {
        const formGroup = service.createFactureEnTeteFormGroup();
        expect(formGroup.controls.id.disabled).toBe(true);

        service.resetForm(formGroup, sampleWithRequiredData);

        expect(formGroup.controls.id.disabled).toBe(true);
      });

      it('passing NewFactureEnTete should disable id FormControl', () => {
        const formGroup = service.createFactureEnTeteFormGroup(sampleWithRequiredData);
        expect(formGroup.controls.id.disabled).toBe(true);

        service.resetForm(formGroup, { id: null });

        expect(formGroup.controls.id.disabled).toBe(true);
      });
    });
  });
});
