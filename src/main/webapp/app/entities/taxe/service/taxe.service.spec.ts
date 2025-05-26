import { TestBed } from '@angular/core/testing';
import { HttpTestingController, provideHttpClientTesting } from '@angular/common/http/testing';
import { provideHttpClient } from '@angular/common/http';

import { ITaxe } from '../taxe.model';
import { sampleWithFullData, sampleWithNewData, sampleWithPartialData, sampleWithRequiredData } from '../taxe.test-samples';

import { TaxeService } from './taxe.service';

const requireRestSample: ITaxe = {
  ...sampleWithRequiredData,
};

describe('Taxe Service', () => {
  let service: TaxeService;
  let httpMock: HttpTestingController;
  let expectedResult: ITaxe | ITaxe[] | boolean | null;

  beforeEach(() => {
    TestBed.configureTestingModule({
      providers: [provideHttpClient(), provideHttpClientTesting()],
    });
    expectedResult = null;
    service = TestBed.inject(TaxeService);
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

    it('should create a Taxe', () => {
      const taxe = { ...sampleWithNewData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.create(taxe).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'POST' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should update a Taxe', () => {
      const taxe = { ...sampleWithRequiredData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.update(taxe).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PUT' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should partial update a Taxe', () => {
      const patchObject = { ...sampleWithPartialData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.partialUpdate(patchObject).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PATCH' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should return a list of Taxe', () => {
      const returnedFromService = { ...requireRestSample };

      const expected = { ...sampleWithRequiredData };

      service.query().subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'GET' });
      req.flush([returnedFromService]);
      httpMock.verify();
      expect(expectedResult).toMatchObject([expected]);
    });

    it('should delete a Taxe', () => {
      const expected = true;

      service.delete(123).subscribe(resp => (expectedResult = resp.ok));

      const req = httpMock.expectOne({ method: 'DELETE' });
      req.flush({ status: 200 });
      expect(expectedResult).toBe(expected);
    });

    describe('addTaxeToCollectionIfMissing', () => {
      it('should add a Taxe to an empty array', () => {
        const taxe: ITaxe = sampleWithRequiredData;
        expectedResult = service.addTaxeToCollectionIfMissing([], taxe);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(taxe);
      });

      it('should not add a Taxe to an array that contains it', () => {
        const taxe: ITaxe = sampleWithRequiredData;
        const taxeCollection: ITaxe[] = [
          {
            ...taxe,
          },
          sampleWithPartialData,
        ];
        expectedResult = service.addTaxeToCollectionIfMissing(taxeCollection, taxe);
        expect(expectedResult).toHaveLength(2);
      });

      it("should add a Taxe to an array that doesn't contain it", () => {
        const taxe: ITaxe = sampleWithRequiredData;
        const taxeCollection: ITaxe[] = [sampleWithPartialData];
        expectedResult = service.addTaxeToCollectionIfMissing(taxeCollection, taxe);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(taxe);
      });

      it('should add only unique Taxe to an array', () => {
        const taxeArray: ITaxe[] = [sampleWithRequiredData, sampleWithPartialData, sampleWithFullData];
        const taxeCollection: ITaxe[] = [sampleWithRequiredData];
        expectedResult = service.addTaxeToCollectionIfMissing(taxeCollection, ...taxeArray);
        expect(expectedResult).toHaveLength(3);
      });

      it('should accept varargs', () => {
        const taxe: ITaxe = sampleWithRequiredData;
        const taxe2: ITaxe = sampleWithPartialData;
        expectedResult = service.addTaxeToCollectionIfMissing([], taxe, taxe2);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(taxe);
        expect(expectedResult).toContain(taxe2);
      });

      it('should accept null and undefined values', () => {
        const taxe: ITaxe = sampleWithRequiredData;
        expectedResult = service.addTaxeToCollectionIfMissing([], null, taxe, undefined);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(taxe);
      });

      it('should return initial array if no Taxe is added', () => {
        const taxeCollection: ITaxe[] = [sampleWithRequiredData];
        expectedResult = service.addTaxeToCollectionIfMissing(taxeCollection, undefined, null);
        expect(expectedResult).toEqual(taxeCollection);
      });
    });

    describe('compareTaxe', () => {
      it('should return true if both entities are null', () => {
        const entity1 = null;
        const entity2 = null;

        const compareResult = service.compareTaxe(entity1, entity2);

        expect(compareResult).toEqual(true);
      });

      it('should return false if one entity is null', () => {
        const entity1 = { id: 11733 };
        const entity2 = null;

        const compareResult1 = service.compareTaxe(entity1, entity2);
        const compareResult2 = service.compareTaxe(entity2, entity1);

        expect(compareResult1).toEqual(false);
        expect(compareResult2).toEqual(false);
      });

      it('should return false if primaryKey differs', () => {
        const entity1 = { id: 11733 };
        const entity2 = { id: 32741 };

        const compareResult1 = service.compareTaxe(entity1, entity2);
        const compareResult2 = service.compareTaxe(entity2, entity1);

        expect(compareResult1).toEqual(false);
        expect(compareResult2).toEqual(false);
      });

      it('should return false if primaryKey matches', () => {
        const entity1 = { id: 11733 };
        const entity2 = { id: 11733 };

        const compareResult1 = service.compareTaxe(entity1, entity2);
        const compareResult2 = service.compareTaxe(entity2, entity1);

        expect(compareResult1).toEqual(true);
        expect(compareResult2).toEqual(true);
      });
    });
  });

  afterEach(() => {
    httpMock.verify();
  });
});
