import { TestBed } from '@angular/core/testing';
import { HttpTestingController, provideHttpClientTesting } from '@angular/common/http/testing';
import { provideHttpClient } from '@angular/common/http';

import { ICodeRaison } from '../code-raison.model';
import { sampleWithFullData, sampleWithNewData, sampleWithPartialData, sampleWithRequiredData } from '../code-raison.test-samples';

import { CodeRaisonService } from './code-raison.service';

const requireRestSample: ICodeRaison = {
  ...sampleWithRequiredData,
};

describe('CodeRaison Service', () => {
  let service: CodeRaisonService;
  let httpMock: HttpTestingController;
  let expectedResult: ICodeRaison | ICodeRaison[] | boolean | null;

  beforeEach(() => {
    TestBed.configureTestingModule({
      providers: [provideHttpClient(), provideHttpClientTesting()],
    });
    expectedResult = null;
    service = TestBed.inject(CodeRaisonService);
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

    it('should create a CodeRaison', () => {
      const codeRaison = { ...sampleWithNewData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.create(codeRaison).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'POST' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should update a CodeRaison', () => {
      const codeRaison = { ...sampleWithRequiredData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.update(codeRaison).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PUT' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should partial update a CodeRaison', () => {
      const patchObject = { ...sampleWithPartialData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.partialUpdate(patchObject).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PATCH' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should return a list of CodeRaison', () => {
      const returnedFromService = { ...requireRestSample };

      const expected = { ...sampleWithRequiredData };

      service.query().subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'GET' });
      req.flush([returnedFromService]);
      httpMock.verify();
      expect(expectedResult).toMatchObject([expected]);
    });

    it('should delete a CodeRaison', () => {
      const expected = true;

      service.delete(123).subscribe(resp => (expectedResult = resp.ok));

      const req = httpMock.expectOne({ method: 'DELETE' });
      req.flush({ status: 200 });
      expect(expectedResult).toBe(expected);
    });

    describe('addCodeRaisonToCollectionIfMissing', () => {
      it('should add a CodeRaison to an empty array', () => {
        const codeRaison: ICodeRaison = sampleWithRequiredData;
        expectedResult = service.addCodeRaisonToCollectionIfMissing([], codeRaison);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(codeRaison);
      });

      it('should not add a CodeRaison to an array that contains it', () => {
        const codeRaison: ICodeRaison = sampleWithRequiredData;
        const codeRaisonCollection: ICodeRaison[] = [
          {
            ...codeRaison,
          },
          sampleWithPartialData,
        ];
        expectedResult = service.addCodeRaisonToCollectionIfMissing(codeRaisonCollection, codeRaison);
        expect(expectedResult).toHaveLength(2);
      });

      it("should add a CodeRaison to an array that doesn't contain it", () => {
        const codeRaison: ICodeRaison = sampleWithRequiredData;
        const codeRaisonCollection: ICodeRaison[] = [sampleWithPartialData];
        expectedResult = service.addCodeRaisonToCollectionIfMissing(codeRaisonCollection, codeRaison);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(codeRaison);
      });

      it('should add only unique CodeRaison to an array', () => {
        const codeRaisonArray: ICodeRaison[] = [sampleWithRequiredData, sampleWithPartialData, sampleWithFullData];
        const codeRaisonCollection: ICodeRaison[] = [sampleWithRequiredData];
        expectedResult = service.addCodeRaisonToCollectionIfMissing(codeRaisonCollection, ...codeRaisonArray);
        expect(expectedResult).toHaveLength(3);
      });

      it('should accept varargs', () => {
        const codeRaison: ICodeRaison = sampleWithRequiredData;
        const codeRaison2: ICodeRaison = sampleWithPartialData;
        expectedResult = service.addCodeRaisonToCollectionIfMissing([], codeRaison, codeRaison2);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(codeRaison);
        expect(expectedResult).toContain(codeRaison2);
      });

      it('should accept null and undefined values', () => {
        const codeRaison: ICodeRaison = sampleWithRequiredData;
        expectedResult = service.addCodeRaisonToCollectionIfMissing([], null, codeRaison, undefined);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(codeRaison);
      });

      it('should return initial array if no CodeRaison is added', () => {
        const codeRaisonCollection: ICodeRaison[] = [sampleWithRequiredData];
        expectedResult = service.addCodeRaisonToCollectionIfMissing(codeRaisonCollection, undefined, null);
        expect(expectedResult).toEqual(codeRaisonCollection);
      });
    });

    describe('compareCodeRaison', () => {
      it('should return true if both entities are null', () => {
        const entity1 = null;
        const entity2 = null;

        const compareResult = service.compareCodeRaison(entity1, entity2);

        expect(compareResult).toEqual(true);
      });

      it('should return false if one entity is null', () => {
        const entity1 = { id: 29266 };
        const entity2 = null;

        const compareResult1 = service.compareCodeRaison(entity1, entity2);
        const compareResult2 = service.compareCodeRaison(entity2, entity1);

        expect(compareResult1).toEqual(false);
        expect(compareResult2).toEqual(false);
      });

      it('should return false if primaryKey differs', () => {
        const entity1 = { id: 29266 };
        const entity2 = { id: 30195 };

        const compareResult1 = service.compareCodeRaison(entity1, entity2);
        const compareResult2 = service.compareCodeRaison(entity2, entity1);

        expect(compareResult1).toEqual(false);
        expect(compareResult2).toEqual(false);
      });

      it('should return false if primaryKey matches', () => {
        const entity1 = { id: 29266 };
        const entity2 = { id: 29266 };

        const compareResult1 = service.compareCodeRaison(entity1, entity2);
        const compareResult2 = service.compareCodeRaison(entity2, entity1);

        expect(compareResult1).toEqual(true);
        expect(compareResult2).toEqual(true);
      });
    });
  });

  afterEach(() => {
    httpMock.verify();
  });
});
