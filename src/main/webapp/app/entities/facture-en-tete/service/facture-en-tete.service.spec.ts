import { TestBed } from '@angular/core/testing';
import { HttpTestingController, provideHttpClientTesting } from '@angular/common/http/testing';
import { provideHttpClient } from '@angular/common/http';

import { IFactureEnTete } from '../facture-en-tete.model';
import { sampleWithFullData, sampleWithNewData, sampleWithPartialData, sampleWithRequiredData } from '../facture-en-tete.test-samples';

import { FactureEnTeteService, RestFactureEnTete } from './facture-en-tete.service';

const requireRestSample: RestFactureEnTete = {
  ...sampleWithRequiredData,
  ouvertureDateTime: sampleWithRequiredData.ouvertureDateTime?.toJSON(),
  fermetureDateTime: sampleWithRequiredData.fermetureDateTime?.toJSON(),
};

describe('FactureEnTete Service', () => {
  let service: FactureEnTeteService;
  let httpMock: HttpTestingController;
  let expectedResult: IFactureEnTete | IFactureEnTete[] | boolean | null;

  beforeEach(() => {
    TestBed.configureTestingModule({
      providers: [provideHttpClient(), provideHttpClientTesting()],
    });
    expectedResult = null;
    service = TestBed.inject(FactureEnTeteService);
    httpMock = TestBed.inject(HttpTestingController);
  });

  describe('Service methods', () => {
    it('should find an element', () => {
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.find(123).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'GET' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should create a FactureEnTete', () => {
      const factureEnTete = { ...sampleWithNewData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.create(factureEnTete).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'POST' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should update a FactureEnTete', () => {
      const factureEnTete = { ...sampleWithRequiredData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.update(factureEnTete).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PUT' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should partial update a FactureEnTete', () => {
      const patchObject = { ...sampleWithPartialData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.partialUpdate(patchObject).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PATCH' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should return a list of FactureEnTete', () => {
      const returnedFromService = { ...requireRestSample };

      const expected = { ...sampleWithRequiredData };

      service.query().subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'GET' });
      req.flush([returnedFromService]);
      httpMock.verify();
      expect(expectedResult).toMatchObject([expected]);
    });

    it('should delete a FactureEnTete', () => {
      const expected = true;

      service.delete(123).subscribe(resp => (expectedResult = resp.ok));

      const req = httpMock.expectOne({ method: 'DELETE' });
      req.flush({ status: 200 });
      expect(expectedResult).toBe(expected);
    });

    describe('addFactureEnTeteToCollectionIfMissing', () => {
      it('should add a FactureEnTete to an empty array', () => {
        const factureEnTete: IFactureEnTete = sampleWithRequiredData;
        expectedResult = service.addFactureEnTeteToCollectionIfMissing([], factureEnTete);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(factureEnTete);
      });

      it('should not add a FactureEnTete to an array that contains it', () => {
        const factureEnTete: IFactureEnTete = sampleWithRequiredData;
        const factureEnTeteCollection: IFactureEnTete[] = [
          {
            ...factureEnTete,
          },
          sampleWithPartialData,
        ];
        expectedResult = service.addFactureEnTeteToCollectionIfMissing(factureEnTeteCollection, factureEnTete);
        expect(expectedResult).toHaveLength(2);
      });

      it("should add a FactureEnTete to an array that doesn't contain it", () => {
        const factureEnTete: IFactureEnTete = sampleWithRequiredData;
        const factureEnTeteCollection: IFactureEnTete[] = [sampleWithPartialData];
        expectedResult = service.addFactureEnTeteToCollectionIfMissing(factureEnTeteCollection, factureEnTete);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(factureEnTete);
      });

      it('should add only unique FactureEnTete to an array', () => {
        const factureEnTeteArray: IFactureEnTete[] = [sampleWithRequiredData, sampleWithPartialData, sampleWithFullData];
        const factureEnTeteCollection: IFactureEnTete[] = [sampleWithRequiredData];
        expectedResult = service.addFactureEnTeteToCollectionIfMissing(factureEnTeteCollection, ...factureEnTeteArray);
        expect(expectedResult).toHaveLength(3);
      });

      it('should accept varargs', () => {
        const factureEnTete: IFactureEnTete = sampleWithRequiredData;
        const factureEnTete2: IFactureEnTete = sampleWithPartialData;
        expectedResult = service.addFactureEnTeteToCollectionIfMissing([], factureEnTete, factureEnTete2);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(factureEnTete);
        expect(expectedResult).toContain(factureEnTete2);
      });

      it('should accept null and undefined values', () => {
        const factureEnTete: IFactureEnTete = sampleWithRequiredData;
        expectedResult = service.addFactureEnTeteToCollectionIfMissing([], null, factureEnTete, undefined);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(factureEnTete);
      });

      it('should return initial array if no FactureEnTete is added', () => {
        const factureEnTeteCollection: IFactureEnTete[] = [sampleWithRequiredData];
        expectedResult = service.addFactureEnTeteToCollectionIfMissing(factureEnTeteCollection, undefined, null);
        expect(expectedResult).toEqual(factureEnTeteCollection);
      });
    });

    describe('compareFactureEnTete', () => {
      it('should return true if both entities are null', () => {
        const entity1 = null;
        const entity2 = null;

        const compareResult = service.compareFactureEnTete(entity1, entity2);

        expect(compareResult).toEqual(true);
      });

      it('should return false if one entity is null', () => {
        const entity1 = { id: 22488 };
        const entity2 = null;

        const compareResult1 = service.compareFactureEnTete(entity1, entity2);
        const compareResult2 = service.compareFactureEnTete(entity2, entity1);

        expect(compareResult1).toEqual(false);
        expect(compareResult2).toEqual(false);
      });

      it('should return false if primaryKey differs', () => {
        const entity1 = { id: 22488 };
        const entity2 = { id: 18092 };

        const compareResult1 = service.compareFactureEnTete(entity1, entity2);
        const compareResult2 = service.compareFactureEnTete(entity2, entity1);

        expect(compareResult1).toEqual(false);
        expect(compareResult2).toEqual(false);
      });

      it('should return false if primaryKey matches', () => {
        const entity1 = { id: 22488 };
        const entity2 = { id: 22488 };

        const compareResult1 = service.compareFactureEnTete(entity1, entity2);
        const compareResult2 = service.compareFactureEnTete(entity2, entity1);

        expect(compareResult1).toEqual(true);
        expect(compareResult2).toEqual(true);
      });
    });
  });

  afterEach(() => {
    httpMock.verify();
  });
});
