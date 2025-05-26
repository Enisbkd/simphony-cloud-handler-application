import { TestBed } from '@angular/core/testing';

import { sampleWithNewData, sampleWithRequiredData } from '../facture-detail.test-samples';

import { FactureDetailFormService } from './facture-detail-form.service';

describe('FactureDetail Form Service', () => {
  let service: FactureDetailFormService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(FactureDetailFormService);
  });

  describe('Service methods', () => {
    describe('createFactureDetailFormGroup', () => {
      it('should create a new form with FormControl', () => {
        const formGroup = service.createFactureDetailFormGroup();

        expect(formGroup.controls).toEqual(
          expect.objectContaining({
            id: expect.any(Object),
            factureEnTeteRef: expect.any(Object),
            numLigne: expect.any(Object),
            detailType: expect.any(Object),
            utcDateTime: expect.any(Object),
            lclDateTime: expect.any(Object),
            numSiege: expect.any(Object),
            niveauPrix: expect.any(Object),
            totalAffiche: expect.any(Object),
            quantiteAffiche: expect.any(Object),
            estErreur: expect.any(Object),
            estNul: expect.any(Object),
            estRetourne: expect.any(Object),
            estInvisible: expect.any(Object),
            totalLigne: expect.any(Object),
            codeRaisonRef: expect.any(Object),
            multiplicateur: expect.any(Object),
            referenceInfo: expect.any(Object),
            referenceInfo2: expect.any(Object),
            partieDeJourneeRef: expect.any(Object),
            periodeDeServiceRef: expect.any(Object),
            numChrono: expect.any(Object),
            parentFactureDetailRef: expect.any(Object),
            taxePourcentage: expect.any(Object),
            taxeMontant: expect.any(Object),
            modePaiementTotal: expect.any(Object),
            prix: expect.any(Object),
            transactionEmployeRef: expect.any(Object),
            transfertEmployeRef: expect.any(Object),
            managerEmployeRef: expect.any(Object),
            repasEmployeRef: expect.any(Object),
            remiseRef: expect.any(Object),
            remiseElementMenuRef: expect.any(Object),
            commissionServiceRef: expect.any(Object),
            modePaiementRef: expect.any(Object),
            elementMenuRef: expect.any(Object),
          }),
        );
      });

      it('passing IFactureDetail should create a new form with FormGroup', () => {
        const formGroup = service.createFactureDetailFormGroup(sampleWithRequiredData);

        expect(formGroup.controls).toEqual(
          expect.objectContaining({
            id: expect.any(Object),
            factureEnTeteRef: expect.any(Object),
            numLigne: expect.any(Object),
            detailType: expect.any(Object),
            utcDateTime: expect.any(Object),
            lclDateTime: expect.any(Object),
            numSiege: expect.any(Object),
            niveauPrix: expect.any(Object),
            totalAffiche: expect.any(Object),
            quantiteAffiche: expect.any(Object),
            estErreur: expect.any(Object),
            estNul: expect.any(Object),
            estRetourne: expect.any(Object),
            estInvisible: expect.any(Object),
            totalLigne: expect.any(Object),
            codeRaisonRef: expect.any(Object),
            multiplicateur: expect.any(Object),
            referenceInfo: expect.any(Object),
            referenceInfo2: expect.any(Object),
            partieDeJourneeRef: expect.any(Object),
            periodeDeServiceRef: expect.any(Object),
            numChrono: expect.any(Object),
            parentFactureDetailRef: expect.any(Object),
            taxePourcentage: expect.any(Object),
            taxeMontant: expect.any(Object),
            modePaiementTotal: expect.any(Object),
            prix: expect.any(Object),
            transactionEmployeRef: expect.any(Object),
            transfertEmployeRef: expect.any(Object),
            managerEmployeRef: expect.any(Object),
            repasEmployeRef: expect.any(Object),
            remiseRef: expect.any(Object),
            remiseElementMenuRef: expect.any(Object),
            commissionServiceRef: expect.any(Object),
            modePaiementRef: expect.any(Object),
            elementMenuRef: expect.any(Object),
          }),
        );
      });
    });

    describe('getFactureDetail', () => {
      it('should return NewFactureDetail for default FactureDetail initial value', () => {
        const formGroup = service.createFactureDetailFormGroup(sampleWithNewData);

        const factureDetail = service.getFactureDetail(formGroup) as any;

        expect(factureDetail).toMatchObject(sampleWithNewData);
      });

      it('should return NewFactureDetail for empty FactureDetail initial value', () => {
        const formGroup = service.createFactureDetailFormGroup();

        const factureDetail = service.getFactureDetail(formGroup) as any;

        expect(factureDetail).toMatchObject({});
      });

      it('should return IFactureDetail', () => {
        const formGroup = service.createFactureDetailFormGroup(sampleWithRequiredData);

        const factureDetail = service.getFactureDetail(formGroup) as any;

        expect(factureDetail).toMatchObject(sampleWithRequiredData);
      });
    });

    describe('resetForm', () => {
      it('passing IFactureDetail should not enable id FormControl', () => {
        const formGroup = service.createFactureDetailFormGroup();
        expect(formGroup.controls.id.disabled).toBe(true);

        service.resetForm(formGroup, sampleWithRequiredData);

        expect(formGroup.controls.id.disabled).toBe(true);
      });

      it('passing NewFactureDetail should disable id FormControl', () => {
        const formGroup = service.createFactureDetailFormGroup(sampleWithRequiredData);
        expect(formGroup.controls.id.disabled).toBe(true);

        service.resetForm(formGroup, { id: null });

        expect(formGroup.controls.id.disabled).toBe(true);
      });
    });
  });
});
