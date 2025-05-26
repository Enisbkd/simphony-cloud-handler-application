import { TestBed } from '@angular/core/testing';
import { HttpTestingController, provideHttpClientTesting } from '@angular/common/http/testing';
import { provideHttpClient } from '@angular/common/http';

import { IFactureDetail } from '../facture-detail.model';
import { sampleWithFullData, sampleWithNewData, sampleWithPartialData, sampleWithRequiredData } from '../facture-detail.test-samples';

import { FactureDetailService, RestFactureDetail } from './facture-detail.service';

const requireRestSample: RestFactureDetail = {
  ...sampleWithRequiredData,
  utcDateTime: sampleWithRequiredData.utcDateTime?.toJSON(),
  lclDateTime: sampleWithRequiredData.lclDateTime?.toJSON(),
};

describe('FactureDetail Service', () => {
  let service: FactureDetailService;
  let httpMock: HttpTestingController;
  let expectedResult: IFactureDetail | IFactureDetail[] | boolean | null;

  beforeEach(() => {
    TestBed.configureTestingModule({
      providers: [provideHttpClient(), provideHttpClientTesting()],
    });
    expectedResult = null;
    service = TestBed.inject(FactureDetailService);
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

    it('should create a FactureDetail', () => {
      const factureDetail = { ...sampleWithNewData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.create(factureDetail).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'POST' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should update a FactureDetail', () => {
      const factureDetail = { ...sampleWithRequiredData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.update(factureDetail).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PUT' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should partial update a FactureDetail', () => {
      const patchObject = { ...sampleWithPartialData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.partialUpdate(patchObject).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PATCH' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should return a list of FactureDetail', () => {
      const returnedFromService = { ...requireRestSample };

      const expected = { ...sampleWithRequiredData };

      service.query().subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'GET' });
      req.flush([returnedFromService]);
      httpMock.verify();
      expect(expectedResult).toMatchObject([expected]);
    });

    it('should delete a FactureDetail', () => {
      const expected = true;

      service.delete(123).subscribe(resp => (expectedResult = resp.ok));

      const req = httpMock.expectOne({ method: 'DELETE' });
      req.flush({ status: 200 });
      expect(expectedResult).toBe(expected);
    });

    describe('addFactureDetailToCollectionIfMissing', () => {
      it('should add a FactureDetail to an empty array', () => {
        const factureDetail: IFactureDetail = sampleWithRequiredData;
        expectedResult = service.addFactureDetailToCollectionIfMissing([], factureDetail);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(factureDetail);
      });

      it('should not add a FactureDetail to an array that contains it', () => {
        const factureDetail: IFactureDetail = sampleWithRequiredData;
        const factureDetailCollection: IFactureDetail[] = [
          {
            ...factureDetail,
          },
          sampleWithPartialData,
        ];
        expectedResult = service.addFactureDetailToCollectionIfMissing(factureDetailCollection, factureDetail);
        expect(expectedResult).toHaveLength(2);
      });

      it("should add a FactureDetail to an array that doesn't contain it", () => {
        const factureDetail: IFactureDetail = sampleWithRequiredData;
        const factureDetailCollection: IFactureDetail[] = [sampleWithPartialData];
        expectedResult = service.addFactureDetailToCollectionIfMissing(factureDetailCollection, factureDetail);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(factureDetail);
      });

      it('should add only unique FactureDetail to an array', () => {
        const factureDetailArray: IFactureDetail[] = [sampleWithRequiredData, sampleWithPartialData, sampleWithFullData];
        const factureDetailCollection: IFactureDetail[] = [sampleWithRequiredData];
        expectedResult = service.addFactureDetailToCollectionIfMissing(factureDetailCollection, ...factureDetailArray);
        expect(expectedResult).toHaveLength(3);
      });

      it('should accept varargs', () => {
        const factureDetail: IFactureDetail = sampleWithRequiredData;
        const factureDetail2: IFactureDetail = sampleWithPartialData;
        expectedResult = service.addFactureDetailToCollectionIfMissing([], factureDetail, factureDetail2);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(factureDetail);
        expect(expectedResult).toContain(factureDetail2);
      });

      it('should accept null and undefined values', () => {
        const factureDetail: IFactureDetail = sampleWithRequiredData;
        expectedResult = service.addFactureDetailToCollectionIfMissing([], null, factureDetail, undefined);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(factureDetail);
      });

      it('should return initial array if no FactureDetail is added', () => {
        const factureDetailCollection: IFactureDetail[] = [sampleWithRequiredData];
        expectedResult = service.addFactureDetailToCollectionIfMissing(factureDetailCollection, undefined, null);
        expect(expectedResult).toEqual(factureDetailCollection);
      });
    });

    describe('compareFactureDetail', () => {
      it('should return true if both entities are null', () => {
        const entity1 = null;
        const entity2 = null;

        const compareResult = service.compareFactureDetail(entity1, entity2);

        expect(compareResult).toEqual(true);
      });

      it('should return false if one entity is null', () => {
        const entity1 = { id: 27600 };
        const entity2 = null;

        const compareResult1 = service.compareFactureDetail(entity1, entity2);
        const compareResult2 = service.compareFactureDetail(entity2, entity1);

        expect(compareResult1).toEqual(false);
        expect(compareResult2).toEqual(false);
      });

      it('should return false if primaryKey differs', () => {
        const entity1 = { id: 27600 };
        const entity2 = { id: 13339 };

        const compareResult1 = service.compareFactureDetail(entity1, entity2);
        const compareResult2 = service.compareFactureDetail(entity2, entity1);

        expect(compareResult1).toEqual(false);
        expect(compareResult2).toEqual(false);
      });

      it('should return false if primaryKey matches', () => {
        const entity1 = { id: 27600 };
        const entity2 = { id: 27600 };

        const compareResult1 = service.compareFactureDetail(entity1, entity2);
        const compareResult2 = service.compareFactureDetail(entity2, entity1);

        expect(compareResult1).toEqual(true);
        expect(compareResult2).toEqual(true);
      });
    });
  });

  afterEach(() => {
    httpMock.verify();
  });
});
