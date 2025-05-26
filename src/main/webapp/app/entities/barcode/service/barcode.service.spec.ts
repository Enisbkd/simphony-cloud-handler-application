import { TestBed } from '@angular/core/testing';
import { HttpTestingController, provideHttpClientTesting } from '@angular/common/http/testing';
import { provideHttpClient } from '@angular/common/http';

import { IBarcode } from '../barcode.model';
import { sampleWithFullData, sampleWithNewData, sampleWithPartialData, sampleWithRequiredData } from '../barcode.test-samples';

import { BarcodeService } from './barcode.service';

const requireRestSample: IBarcode = {
  ...sampleWithRequiredData,
};

describe('Barcode Service', () => {
  let service: BarcodeService;
  let httpMock: HttpTestingController;
  let expectedResult: IBarcode | IBarcode[] | boolean | null;

  beforeEach(() => {
    TestBed.configureTestingModule({
      providers: [provideHttpClient(), provideHttpClientTesting()],
    });
    expectedResult = null;
    service = TestBed.inject(BarcodeService);
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

    it('should create a Barcode', () => {
      const barcode = { ...sampleWithNewData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.create(barcode).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'POST' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should update a Barcode', () => {
      const barcode = { ...sampleWithRequiredData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.update(barcode).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PUT' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should partial update a Barcode', () => {
      const patchObject = { ...sampleWithPartialData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.partialUpdate(patchObject).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PATCH' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should return a list of Barcode', () => {
      const returnedFromService = { ...requireRestSample };

      const expected = { ...sampleWithRequiredData };

      service.query().subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'GET' });
      req.flush([returnedFromService]);
      httpMock.verify();
      expect(expectedResult).toMatchObject([expected]);
    });

    it('should delete a Barcode', () => {
      const expected = true;

      service.delete(123).subscribe(resp => (expectedResult = resp.ok));

      const req = httpMock.expectOne({ method: 'DELETE' });
      req.flush({ status: 200 });
      expect(expectedResult).toBe(expected);
    });

    describe('addBarcodeToCollectionIfMissing', () => {
      it('should add a Barcode to an empty array', () => {
        const barcode: IBarcode = sampleWithRequiredData;
        expectedResult = service.addBarcodeToCollectionIfMissing([], barcode);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(barcode);
      });

      it('should not add a Barcode to an array that contains it', () => {
        const barcode: IBarcode = sampleWithRequiredData;
        const barcodeCollection: IBarcode[] = [
          {
            ...barcode,
          },
          sampleWithPartialData,
        ];
        expectedResult = service.addBarcodeToCollectionIfMissing(barcodeCollection, barcode);
        expect(expectedResult).toHaveLength(2);
      });

      it("should add a Barcode to an array that doesn't contain it", () => {
        const barcode: IBarcode = sampleWithRequiredData;
        const barcodeCollection: IBarcode[] = [sampleWithPartialData];
        expectedResult = service.addBarcodeToCollectionIfMissing(barcodeCollection, barcode);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(barcode);
      });

      it('should add only unique Barcode to an array', () => {
        const barcodeArray: IBarcode[] = [sampleWithRequiredData, sampleWithPartialData, sampleWithFullData];
        const barcodeCollection: IBarcode[] = [sampleWithRequiredData];
        expectedResult = service.addBarcodeToCollectionIfMissing(barcodeCollection, ...barcodeArray);
        expect(expectedResult).toHaveLength(3);
      });

      it('should accept varargs', () => {
        const barcode: IBarcode = sampleWithRequiredData;
        const barcode2: IBarcode = sampleWithPartialData;
        expectedResult = service.addBarcodeToCollectionIfMissing([], barcode, barcode2);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(barcode);
        expect(expectedResult).toContain(barcode2);
      });

      it('should accept null and undefined values', () => {
        const barcode: IBarcode = sampleWithRequiredData;
        expectedResult = service.addBarcodeToCollectionIfMissing([], null, barcode, undefined);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(barcode);
      });

      it('should return initial array if no Barcode is added', () => {
        const barcodeCollection: IBarcode[] = [sampleWithRequiredData];
        expectedResult = service.addBarcodeToCollectionIfMissing(barcodeCollection, undefined, null);
        expect(expectedResult).toEqual(barcodeCollection);
      });
    });

    describe('compareBarcode', () => {
      it('should return true if both entities are null', () => {
        const entity1 = null;
        const entity2 = null;

        const compareResult = service.compareBarcode(entity1, entity2);

        expect(compareResult).toEqual(true);
      });

      it('should return false if one entity is null', () => {
        const entity1 = { id: 17083 };
        const entity2 = null;

        const compareResult1 = service.compareBarcode(entity1, entity2);
        const compareResult2 = service.compareBarcode(entity2, entity1);

        expect(compareResult1).toEqual(false);
        expect(compareResult2).toEqual(false);
      });

      it('should return false if primaryKey differs', () => {
        const entity1 = { id: 17083 };
        const entity2 = { id: 18666 };

        const compareResult1 = service.compareBarcode(entity1, entity2);
        const compareResult2 = service.compareBarcode(entity2, entity1);

        expect(compareResult1).toEqual(false);
        expect(compareResult2).toEqual(false);
      });

      it('should return false if primaryKey matches', () => {
        const entity1 = { id: 17083 };
        const entity2 = { id: 17083 };

        const compareResult1 = service.compareBarcode(entity1, entity2);
        const compareResult2 = service.compareBarcode(entity2, entity1);

        expect(compareResult1).toEqual(true);
        expect(compareResult2).toEqual(true);
      });
    });
  });

  afterEach(() => {
    httpMock.verify();
  });
});
