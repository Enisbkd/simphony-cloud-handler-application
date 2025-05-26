import { TestBed } from '@angular/core/testing';
import { HttpTestingController, provideHttpClientTesting } from '@angular/common/http/testing';
import { provideHttpClient } from '@angular/common/http';

import { ICommissionService } from '../commission-service.model';
import { sampleWithFullData, sampleWithNewData, sampleWithPartialData, sampleWithRequiredData } from '../commission-service.test-samples';

import { CommissionServiceService } from './commission-service.service';

const requireRestSample: ICommissionService = {
  ...sampleWithRequiredData,
};

describe('CommissionService Service', () => {
  let service: CommissionServiceService;
  let httpMock: HttpTestingController;
  let expectedResult: ICommissionService | ICommissionService[] | boolean | null;

  beforeEach(() => {
    TestBed.configureTestingModule({
      providers: [provideHttpClient(), provideHttpClientTesting()],
    });
    expectedResult = null;
    service = TestBed.inject(CommissionServiceService);
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

    it('should create a CommissionService', () => {
      const commissionService = { ...sampleWithNewData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.create(commissionService).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'POST' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should update a CommissionService', () => {
      const commissionService = { ...sampleWithRequiredData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.update(commissionService).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PUT' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should partial update a CommissionService', () => {
      const patchObject = { ...sampleWithPartialData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.partialUpdate(patchObject).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PATCH' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should return a list of CommissionService', () => {
      const returnedFromService = { ...requireRestSample };

      const expected = { ...sampleWithRequiredData };

      service.query().subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'GET' });
      req.flush([returnedFromService]);
      httpMock.verify();
      expect(expectedResult).toMatchObject([expected]);
    });

    it('should delete a CommissionService', () => {
      const expected = true;

      service.delete(123).subscribe(resp => (expectedResult = resp.ok));

      const req = httpMock.expectOne({ method: 'DELETE' });
      req.flush({ status: 200 });
      expect(expectedResult).toBe(expected);
    });

    describe('addCommissionServiceToCollectionIfMissing', () => {
      it('should add a CommissionService to an empty array', () => {
        const commissionService: ICommissionService = sampleWithRequiredData;
        expectedResult = service.addCommissionServiceToCollectionIfMissing([], commissionService);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(commissionService);
      });

      it('should not add a CommissionService to an array that contains it', () => {
        const commissionService: ICommissionService = sampleWithRequiredData;
        const commissionServiceCollection: ICommissionService[] = [
          {
            ...commissionService,
          },
          sampleWithPartialData,
        ];
        expectedResult = service.addCommissionServiceToCollectionIfMissing(commissionServiceCollection, commissionService);
        expect(expectedResult).toHaveLength(2);
      });

      it("should add a CommissionService to an array that doesn't contain it", () => {
        const commissionService: ICommissionService = sampleWithRequiredData;
        const commissionServiceCollection: ICommissionService[] = [sampleWithPartialData];
        expectedResult = service.addCommissionServiceToCollectionIfMissing(commissionServiceCollection, commissionService);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(commissionService);
      });

      it('should add only unique CommissionService to an array', () => {
        const commissionServiceArray: ICommissionService[] = [sampleWithRequiredData, sampleWithPartialData, sampleWithFullData];
        const commissionServiceCollection: ICommissionService[] = [sampleWithRequiredData];
        expectedResult = service.addCommissionServiceToCollectionIfMissing(commissionServiceCollection, ...commissionServiceArray);
        expect(expectedResult).toHaveLength(3);
      });

      it('should accept varargs', () => {
        const commissionService: ICommissionService = sampleWithRequiredData;
        const commissionService2: ICommissionService = sampleWithPartialData;
        expectedResult = service.addCommissionServiceToCollectionIfMissing([], commissionService, commissionService2);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(commissionService);
        expect(expectedResult).toContain(commissionService2);
      });

      it('should accept null and undefined values', () => {
        const commissionService: ICommissionService = sampleWithRequiredData;
        expectedResult = service.addCommissionServiceToCollectionIfMissing([], null, commissionService, undefined);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(commissionService);
      });

      it('should return initial array if no CommissionService is added', () => {
        const commissionServiceCollection: ICommissionService[] = [sampleWithRequiredData];
        expectedResult = service.addCommissionServiceToCollectionIfMissing(commissionServiceCollection, undefined, null);
        expect(expectedResult).toEqual(commissionServiceCollection);
      });
    });

    describe('compareCommissionService', () => {
      it('should return true if both entities are null', () => {
        const entity1 = null;
        const entity2 = null;

        const compareResult = service.compareCommissionService(entity1, entity2);

        expect(compareResult).toEqual(true);
      });

      it('should return false if one entity is null', () => {
        const entity1 = { id: 19824 };
        const entity2 = null;

        const compareResult1 = service.compareCommissionService(entity1, entity2);
        const compareResult2 = service.compareCommissionService(entity2, entity1);

        expect(compareResult1).toEqual(false);
        expect(compareResult2).toEqual(false);
      });

      it('should return false if primaryKey differs', () => {
        const entity1 = { id: 19824 };
        const entity2 = { id: 16010 };

        const compareResult1 = service.compareCommissionService(entity1, entity2);
        const compareResult2 = service.compareCommissionService(entity2, entity1);

        expect(compareResult1).toEqual(false);
        expect(compareResult2).toEqual(false);
      });

      it('should return false if primaryKey matches', () => {
        const entity1 = { id: 19824 };
        const entity2 = { id: 19824 };

        const compareResult1 = service.compareCommissionService(entity1, entity2);
        const compareResult2 = service.compareCommissionService(entity2, entity1);

        expect(compareResult1).toEqual(true);
        expect(compareResult2).toEqual(true);
      });
    });
  });

  afterEach(() => {
    httpMock.verify();
  });
});
