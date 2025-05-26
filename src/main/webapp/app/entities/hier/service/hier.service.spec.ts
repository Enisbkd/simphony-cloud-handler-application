import { TestBed } from '@angular/core/testing';
import { HttpTestingController, provideHttpClientTesting } from '@angular/common/http/testing';
import { provideHttpClient } from '@angular/common/http';

import { IHier } from '../hier.model';
import { sampleWithFullData, sampleWithNewData, sampleWithPartialData, sampleWithRequiredData } from '../hier.test-samples';

import { HierService } from './hier.service';

const requireRestSample: IHier = {
  ...sampleWithRequiredData,
};

describe('Hier Service', () => {
  let service: HierService;
  let httpMock: HttpTestingController;
  let expectedResult: IHier | IHier[] | boolean | null;

  beforeEach(() => {
    TestBed.configureTestingModule({
      providers: [provideHttpClient(), provideHttpClientTesting()],
    });
    expectedResult = null;
    service = TestBed.inject(HierService);
    httpMock = TestBed.inject(HttpTestingController);
  });

  describe('Service methods', () => {
    it('should find an element', () => {
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.find('ABC').subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'GET' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should create a Hier', () => {
      const hier = { ...sampleWithNewData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.create(hier).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'POST' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should update a Hier', () => {
      const hier = { ...sampleWithRequiredData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.update(hier).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PUT' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should partial update a Hier', () => {
      const patchObject = { ...sampleWithPartialData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.partialUpdate(patchObject).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PATCH' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should return a list of Hier', () => {
      const returnedFromService = { ...requireRestSample };

      const expected = { ...sampleWithRequiredData };

      service.query().subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'GET' });
      req.flush([returnedFromService]);
      httpMock.verify();
      expect(expectedResult).toMatchObject([expected]);
    });

    it('should delete a Hier', () => {
      const expected = true;

      service.delete('ABC').subscribe(resp => (expectedResult = resp.ok));

      const req = httpMock.expectOne({ method: 'DELETE' });
      req.flush({ status: 200 });
      expect(expectedResult).toBe(expected);
    });

    describe('addHierToCollectionIfMissing', () => {
      it('should add a Hier to an empty array', () => {
        const hier: IHier = sampleWithRequiredData;
        expectedResult = service.addHierToCollectionIfMissing([], hier);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(hier);
      });

      it('should not add a Hier to an array that contains it', () => {
        const hier: IHier = sampleWithRequiredData;
        const hierCollection: IHier[] = [
          {
            ...hier,
          },
          sampleWithPartialData,
        ];
        expectedResult = service.addHierToCollectionIfMissing(hierCollection, hier);
        expect(expectedResult).toHaveLength(2);
      });

      it("should add a Hier to an array that doesn't contain it", () => {
        const hier: IHier = sampleWithRequiredData;
        const hierCollection: IHier[] = [sampleWithPartialData];
        expectedResult = service.addHierToCollectionIfMissing(hierCollection, hier);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(hier);
      });

      it('should add only unique Hier to an array', () => {
        const hierArray: IHier[] = [sampleWithRequiredData, sampleWithPartialData, sampleWithFullData];
        const hierCollection: IHier[] = [sampleWithRequiredData];
        expectedResult = service.addHierToCollectionIfMissing(hierCollection, ...hierArray);
        expect(expectedResult).toHaveLength(3);
      });

      it('should accept varargs', () => {
        const hier: IHier = sampleWithRequiredData;
        const hier2: IHier = sampleWithPartialData;
        expectedResult = service.addHierToCollectionIfMissing([], hier, hier2);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(hier);
        expect(expectedResult).toContain(hier2);
      });

      it('should accept null and undefined values', () => {
        const hier: IHier = sampleWithRequiredData;
        expectedResult = service.addHierToCollectionIfMissing([], null, hier, undefined);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(hier);
      });

      it('should return initial array if no Hier is added', () => {
        const hierCollection: IHier[] = [sampleWithRequiredData];
        expectedResult = service.addHierToCollectionIfMissing(hierCollection, undefined, null);
        expect(expectedResult).toEqual(hierCollection);
      });
    });

    describe('compareHier', () => {
      it('should return true if both entities are null', () => {
        const entity1 = null;
        const entity2 = null;

        const compareResult = service.compareHier(entity1, entity2);

        expect(compareResult).toEqual(true);
      });

      it('should return false if one entity is null', () => {
        const entity1 = { id: '5b7fea44-24f3-4956-abaa-0999154e9c91' };
        const entity2 = null;

        const compareResult1 = service.compareHier(entity1, entity2);
        const compareResult2 = service.compareHier(entity2, entity1);

        expect(compareResult1).toEqual(false);
        expect(compareResult2).toEqual(false);
      });

      it('should return false if primaryKey differs', () => {
        const entity1 = { id: '5b7fea44-24f3-4956-abaa-0999154e9c91' };
        const entity2 = { id: '099de192-b562-45ef-9f3e-f62533c56391' };

        const compareResult1 = service.compareHier(entity1, entity2);
        const compareResult2 = service.compareHier(entity2, entity1);

        expect(compareResult1).toEqual(false);
        expect(compareResult2).toEqual(false);
      });

      it('should return false if primaryKey matches', () => {
        const entity1 = { id: '5b7fea44-24f3-4956-abaa-0999154e9c91' };
        const entity2 = { id: '5b7fea44-24f3-4956-abaa-0999154e9c91' };

        const compareResult1 = service.compareHier(entity1, entity2);
        const compareResult2 = service.compareHier(entity2, entity1);

        expect(compareResult1).toEqual(true);
        expect(compareResult2).toEqual(true);
      });
    });
  });

  afterEach(() => {
    httpMock.verify();
  });
});
