import { TestBed } from '@angular/core/testing';
import { HttpTestingController, provideHttpClientTesting } from '@angular/common/http/testing';
import { provideHttpClient } from '@angular/common/http';

import { IPartieDeJournee } from '../partie-de-journee.model';
import { sampleWithFullData, sampleWithNewData, sampleWithPartialData, sampleWithRequiredData } from '../partie-de-journee.test-samples';

import { PartieDeJourneeService } from './partie-de-journee.service';

const requireRestSample: IPartieDeJournee = {
  ...sampleWithRequiredData,
};

describe('PartieDeJournee Service', () => {
  let service: PartieDeJourneeService;
  let httpMock: HttpTestingController;
  let expectedResult: IPartieDeJournee | IPartieDeJournee[] | boolean | null;

  beforeEach(() => {
    TestBed.configureTestingModule({
      providers: [provideHttpClient(), provideHttpClientTesting()],
    });
    expectedResult = null;
    service = TestBed.inject(PartieDeJourneeService);
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

    it('should create a PartieDeJournee', () => {
      const partieDeJournee = { ...sampleWithNewData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.create(partieDeJournee).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'POST' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should update a PartieDeJournee', () => {
      const partieDeJournee = { ...sampleWithRequiredData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.update(partieDeJournee).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PUT' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should partial update a PartieDeJournee', () => {
      const patchObject = { ...sampleWithPartialData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.partialUpdate(patchObject).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PATCH' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should return a list of PartieDeJournee', () => {
      const returnedFromService = { ...requireRestSample };

      const expected = { ...sampleWithRequiredData };

      service.query().subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'GET' });
      req.flush([returnedFromService]);
      httpMock.verify();
      expect(expectedResult).toMatchObject([expected]);
    });

    it('should delete a PartieDeJournee', () => {
      const expected = true;

      service.delete(123).subscribe(resp => (expectedResult = resp.ok));

      const req = httpMock.expectOne({ method: 'DELETE' });
      req.flush({ status: 200 });
      expect(expectedResult).toBe(expected);
    });

    describe('addPartieDeJourneeToCollectionIfMissing', () => {
      it('should add a PartieDeJournee to an empty array', () => {
        const partieDeJournee: IPartieDeJournee = sampleWithRequiredData;
        expectedResult = service.addPartieDeJourneeToCollectionIfMissing([], partieDeJournee);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(partieDeJournee);
      });

      it('should not add a PartieDeJournee to an array that contains it', () => {
        const partieDeJournee: IPartieDeJournee = sampleWithRequiredData;
        const partieDeJourneeCollection: IPartieDeJournee[] = [
          {
            ...partieDeJournee,
          },
          sampleWithPartialData,
        ];
        expectedResult = service.addPartieDeJourneeToCollectionIfMissing(partieDeJourneeCollection, partieDeJournee);
        expect(expectedResult).toHaveLength(2);
      });

      it("should add a PartieDeJournee to an array that doesn't contain it", () => {
        const partieDeJournee: IPartieDeJournee = sampleWithRequiredData;
        const partieDeJourneeCollection: IPartieDeJournee[] = [sampleWithPartialData];
        expectedResult = service.addPartieDeJourneeToCollectionIfMissing(partieDeJourneeCollection, partieDeJournee);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(partieDeJournee);
      });

      it('should add only unique PartieDeJournee to an array', () => {
        const partieDeJourneeArray: IPartieDeJournee[] = [sampleWithRequiredData, sampleWithPartialData, sampleWithFullData];
        const partieDeJourneeCollection: IPartieDeJournee[] = [sampleWithRequiredData];
        expectedResult = service.addPartieDeJourneeToCollectionIfMissing(partieDeJourneeCollection, ...partieDeJourneeArray);
        expect(expectedResult).toHaveLength(3);
      });

      it('should accept varargs', () => {
        const partieDeJournee: IPartieDeJournee = sampleWithRequiredData;
        const partieDeJournee2: IPartieDeJournee = sampleWithPartialData;
        expectedResult = service.addPartieDeJourneeToCollectionIfMissing([], partieDeJournee, partieDeJournee2);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(partieDeJournee);
        expect(expectedResult).toContain(partieDeJournee2);
      });

      it('should accept null and undefined values', () => {
        const partieDeJournee: IPartieDeJournee = sampleWithRequiredData;
        expectedResult = service.addPartieDeJourneeToCollectionIfMissing([], null, partieDeJournee, undefined);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(partieDeJournee);
      });

      it('should return initial array if no PartieDeJournee is added', () => {
        const partieDeJourneeCollection: IPartieDeJournee[] = [sampleWithRequiredData];
        expectedResult = service.addPartieDeJourneeToCollectionIfMissing(partieDeJourneeCollection, undefined, null);
        expect(expectedResult).toEqual(partieDeJourneeCollection);
      });
    });

    describe('comparePartieDeJournee', () => {
      it('should return true if both entities are null', () => {
        const entity1 = null;
        const entity2 = null;

        const compareResult = service.comparePartieDeJournee(entity1, entity2);

        expect(compareResult).toEqual(true);
      });

      it('should return false if one entity is null', () => {
        const entity1 = { id: 24117 };
        const entity2 = null;

        const compareResult1 = service.comparePartieDeJournee(entity1, entity2);
        const compareResult2 = service.comparePartieDeJournee(entity2, entity1);

        expect(compareResult1).toEqual(false);
        expect(compareResult2).toEqual(false);
      });

      it('should return false if primaryKey differs', () => {
        const entity1 = { id: 24117 };
        const entity2 = { id: 25600 };

        const compareResult1 = service.comparePartieDeJournee(entity1, entity2);
        const compareResult2 = service.comparePartieDeJournee(entity2, entity1);

        expect(compareResult1).toEqual(false);
        expect(compareResult2).toEqual(false);
      });

      it('should return false if primaryKey matches', () => {
        const entity1 = { id: 24117 };
        const entity2 = { id: 24117 };

        const compareResult1 = service.comparePartieDeJournee(entity1, entity2);
        const compareResult2 = service.comparePartieDeJournee(entity2, entity1);

        expect(compareResult1).toEqual(true);
        expect(compareResult2).toEqual(true);
      });
    });
  });

  afterEach(() => {
    httpMock.verify();
  });
});
