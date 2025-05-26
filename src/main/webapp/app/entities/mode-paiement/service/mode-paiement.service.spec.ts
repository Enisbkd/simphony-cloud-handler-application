import { TestBed } from '@angular/core/testing';
import { HttpTestingController, provideHttpClientTesting } from '@angular/common/http/testing';
import { provideHttpClient } from '@angular/common/http';

import { IModePaiement } from '../mode-paiement.model';
import { sampleWithFullData, sampleWithNewData, sampleWithPartialData, sampleWithRequiredData } from '../mode-paiement.test-samples';

import { ModePaiementService } from './mode-paiement.service';

const requireRestSample: IModePaiement = {
  ...sampleWithRequiredData,
};

describe('ModePaiement Service', () => {
  let service: ModePaiementService;
  let httpMock: HttpTestingController;
  let expectedResult: IModePaiement | IModePaiement[] | boolean | null;

  beforeEach(() => {
    TestBed.configureTestingModule({
      providers: [provideHttpClient(), provideHttpClientTesting()],
    });
    expectedResult = null;
    service = TestBed.inject(ModePaiementService);
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

    it('should create a ModePaiement', () => {
      const modePaiement = { ...sampleWithNewData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.create(modePaiement).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'POST' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should update a ModePaiement', () => {
      const modePaiement = { ...sampleWithRequiredData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.update(modePaiement).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PUT' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should partial update a ModePaiement', () => {
      const patchObject = { ...sampleWithPartialData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.partialUpdate(patchObject).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PATCH' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should return a list of ModePaiement', () => {
      const returnedFromService = { ...requireRestSample };

      const expected = { ...sampleWithRequiredData };

      service.query().subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'GET' });
      req.flush([returnedFromService]);
      httpMock.verify();
      expect(expectedResult).toMatchObject([expected]);
    });

    it('should delete a ModePaiement', () => {
      const expected = true;

      service.delete(123).subscribe(resp => (expectedResult = resp.ok));

      const req = httpMock.expectOne({ method: 'DELETE' });
      req.flush({ status: 200 });
      expect(expectedResult).toBe(expected);
    });

    describe('addModePaiementToCollectionIfMissing', () => {
      it('should add a ModePaiement to an empty array', () => {
        const modePaiement: IModePaiement = sampleWithRequiredData;
        expectedResult = service.addModePaiementToCollectionIfMissing([], modePaiement);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(modePaiement);
      });

      it('should not add a ModePaiement to an array that contains it', () => {
        const modePaiement: IModePaiement = sampleWithRequiredData;
        const modePaiementCollection: IModePaiement[] = [
          {
            ...modePaiement,
          },
          sampleWithPartialData,
        ];
        expectedResult = service.addModePaiementToCollectionIfMissing(modePaiementCollection, modePaiement);
        expect(expectedResult).toHaveLength(2);
      });

      it("should add a ModePaiement to an array that doesn't contain it", () => {
        const modePaiement: IModePaiement = sampleWithRequiredData;
        const modePaiementCollection: IModePaiement[] = [sampleWithPartialData];
        expectedResult = service.addModePaiementToCollectionIfMissing(modePaiementCollection, modePaiement);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(modePaiement);
      });

      it('should add only unique ModePaiement to an array', () => {
        const modePaiementArray: IModePaiement[] = [sampleWithRequiredData, sampleWithPartialData, sampleWithFullData];
        const modePaiementCollection: IModePaiement[] = [sampleWithRequiredData];
        expectedResult = service.addModePaiementToCollectionIfMissing(modePaiementCollection, ...modePaiementArray);
        expect(expectedResult).toHaveLength(3);
      });

      it('should accept varargs', () => {
        const modePaiement: IModePaiement = sampleWithRequiredData;
        const modePaiement2: IModePaiement = sampleWithPartialData;
        expectedResult = service.addModePaiementToCollectionIfMissing([], modePaiement, modePaiement2);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(modePaiement);
        expect(expectedResult).toContain(modePaiement2);
      });

      it('should accept null and undefined values', () => {
        const modePaiement: IModePaiement = sampleWithRequiredData;
        expectedResult = service.addModePaiementToCollectionIfMissing([], null, modePaiement, undefined);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(modePaiement);
      });

      it('should return initial array if no ModePaiement is added', () => {
        const modePaiementCollection: IModePaiement[] = [sampleWithRequiredData];
        expectedResult = service.addModePaiementToCollectionIfMissing(modePaiementCollection, undefined, null);
        expect(expectedResult).toEqual(modePaiementCollection);
      });
    });

    describe('compareModePaiement', () => {
      it('should return true if both entities are null', () => {
        const entity1 = null;
        const entity2 = null;

        const compareResult = service.compareModePaiement(entity1, entity2);

        expect(compareResult).toEqual(true);
      });

      it('should return false if one entity is null', () => {
        const entity1 = { id: 18358 };
        const entity2 = null;

        const compareResult1 = service.compareModePaiement(entity1, entity2);
        const compareResult2 = service.compareModePaiement(entity2, entity1);

        expect(compareResult1).toEqual(false);
        expect(compareResult2).toEqual(false);
      });

      it('should return false if primaryKey differs', () => {
        const entity1 = { id: 18358 };
        const entity2 = { id: 32260 };

        const compareResult1 = service.compareModePaiement(entity1, entity2);
        const compareResult2 = service.compareModePaiement(entity2, entity1);

        expect(compareResult1).toEqual(false);
        expect(compareResult2).toEqual(false);
      });

      it('should return false if primaryKey matches', () => {
        const entity1 = { id: 18358 };
        const entity2 = { id: 18358 };

        const compareResult1 = service.compareModePaiement(entity1, entity2);
        const compareResult2 = service.compareModePaiement(entity2, entity1);

        expect(compareResult1).toEqual(true);
        expect(compareResult2).toEqual(true);
      });
    });
  });

  afterEach(() => {
    httpMock.verify();
  });
});
