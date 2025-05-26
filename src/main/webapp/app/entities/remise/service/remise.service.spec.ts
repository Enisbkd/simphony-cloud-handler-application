import { TestBed } from '@angular/core/testing';
import { HttpTestingController, provideHttpClientTesting } from '@angular/common/http/testing';
import { provideHttpClient } from '@angular/common/http';

import { IRemise } from '../remise.model';
import { sampleWithFullData, sampleWithNewData, sampleWithPartialData, sampleWithRequiredData } from '../remise.test-samples';

import { RemiseService } from './remise.service';

const requireRestSample: IRemise = {
  ...sampleWithRequiredData,
};

describe('Remise Service', () => {
  let service: RemiseService;
  let httpMock: HttpTestingController;
  let expectedResult: IRemise | IRemise[] | boolean | null;

  beforeEach(() => {
    TestBed.configureTestingModule({
      providers: [provideHttpClient(), provideHttpClientTesting()],
    });
    expectedResult = null;
    service = TestBed.inject(RemiseService);
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

    it('should create a Remise', () => {
      const remise = { ...sampleWithNewData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.create(remise).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'POST' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should update a Remise', () => {
      const remise = { ...sampleWithRequiredData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.update(remise).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PUT' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should partial update a Remise', () => {
      const patchObject = { ...sampleWithPartialData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.partialUpdate(patchObject).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PATCH' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should return a list of Remise', () => {
      const returnedFromService = { ...requireRestSample };

      const expected = { ...sampleWithRequiredData };

      service.query().subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'GET' });
      req.flush([returnedFromService]);
      httpMock.verify();
      expect(expectedResult).toMatchObject([expected]);
    });

    it('should delete a Remise', () => {
      const expected = true;

      service.delete(123).subscribe(resp => (expectedResult = resp.ok));

      const req = httpMock.expectOne({ method: 'DELETE' });
      req.flush({ status: 200 });
      expect(expectedResult).toBe(expected);
    });

    describe('addRemiseToCollectionIfMissing', () => {
      it('should add a Remise to an empty array', () => {
        const remise: IRemise = sampleWithRequiredData;
        expectedResult = service.addRemiseToCollectionIfMissing([], remise);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(remise);
      });

      it('should not add a Remise to an array that contains it', () => {
        const remise: IRemise = sampleWithRequiredData;
        const remiseCollection: IRemise[] = [
          {
            ...remise,
          },
          sampleWithPartialData,
        ];
        expectedResult = service.addRemiseToCollectionIfMissing(remiseCollection, remise);
        expect(expectedResult).toHaveLength(2);
      });

      it("should add a Remise to an array that doesn't contain it", () => {
        const remise: IRemise = sampleWithRequiredData;
        const remiseCollection: IRemise[] = [sampleWithPartialData];
        expectedResult = service.addRemiseToCollectionIfMissing(remiseCollection, remise);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(remise);
      });

      it('should add only unique Remise to an array', () => {
        const remiseArray: IRemise[] = [sampleWithRequiredData, sampleWithPartialData, sampleWithFullData];
        const remiseCollection: IRemise[] = [sampleWithRequiredData];
        expectedResult = service.addRemiseToCollectionIfMissing(remiseCollection, ...remiseArray);
        expect(expectedResult).toHaveLength(3);
      });

      it('should accept varargs', () => {
        const remise: IRemise = sampleWithRequiredData;
        const remise2: IRemise = sampleWithPartialData;
        expectedResult = service.addRemiseToCollectionIfMissing([], remise, remise2);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(remise);
        expect(expectedResult).toContain(remise2);
      });

      it('should accept null and undefined values', () => {
        const remise: IRemise = sampleWithRequiredData;
        expectedResult = service.addRemiseToCollectionIfMissing([], null, remise, undefined);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(remise);
      });

      it('should return initial array if no Remise is added', () => {
        const remiseCollection: IRemise[] = [sampleWithRequiredData];
        expectedResult = service.addRemiseToCollectionIfMissing(remiseCollection, undefined, null);
        expect(expectedResult).toEqual(remiseCollection);
      });
    });

    describe('compareRemise', () => {
      it('should return true if both entities are null', () => {
        const entity1 = null;
        const entity2 = null;

        const compareResult = service.compareRemise(entity1, entity2);

        expect(compareResult).toEqual(true);
      });

      it('should return false if one entity is null', () => {
        const entity1 = { id: 4087 };
        const entity2 = null;

        const compareResult1 = service.compareRemise(entity1, entity2);
        const compareResult2 = service.compareRemise(entity2, entity1);

        expect(compareResult1).toEqual(false);
        expect(compareResult2).toEqual(false);
      });

      it('should return false if primaryKey differs', () => {
        const entity1 = { id: 4087 };
        const entity2 = { id: 1654 };

        const compareResult1 = service.compareRemise(entity1, entity2);
        const compareResult2 = service.compareRemise(entity2, entity1);

        expect(compareResult1).toEqual(false);
        expect(compareResult2).toEqual(false);
      });

      it('should return false if primaryKey matches', () => {
        const entity1 = { id: 4087 };
        const entity2 = { id: 4087 };

        const compareResult1 = service.compareRemise(entity1, entity2);
        const compareResult2 = service.compareRemise(entity2, entity1);

        expect(compareResult1).toEqual(true);
        expect(compareResult2).toEqual(true);
      });
    });
  });

  afterEach(() => {
    httpMock.verify();
  });
});
