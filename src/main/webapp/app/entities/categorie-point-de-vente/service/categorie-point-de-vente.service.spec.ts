import { TestBed } from '@angular/core/testing';
import { HttpTestingController, provideHttpClientTesting } from '@angular/common/http/testing';
import { provideHttpClient } from '@angular/common/http';

import { ICategoriePointDeVente } from '../categorie-point-de-vente.model';
import {
  sampleWithFullData,
  sampleWithNewData,
  sampleWithPartialData,
  sampleWithRequiredData,
} from '../categorie-point-de-vente.test-samples';

import { CategoriePointDeVenteService } from './categorie-point-de-vente.service';

const requireRestSample: ICategoriePointDeVente = {
  ...sampleWithRequiredData,
};

describe('CategoriePointDeVente Service', () => {
  let service: CategoriePointDeVenteService;
  let httpMock: HttpTestingController;
  let expectedResult: ICategoriePointDeVente | ICategoriePointDeVente[] | boolean | null;

  beforeEach(() => {
    TestBed.configureTestingModule({
      providers: [provideHttpClient(), provideHttpClientTesting()],
    });
    expectedResult = null;
    service = TestBed.inject(CategoriePointDeVenteService);
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

    it('should create a CategoriePointDeVente', () => {
      const categoriePointDeVente = { ...sampleWithNewData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.create(categoriePointDeVente).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'POST' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should update a CategoriePointDeVente', () => {
      const categoriePointDeVente = { ...sampleWithRequiredData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.update(categoriePointDeVente).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PUT' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should partial update a CategoriePointDeVente', () => {
      const patchObject = { ...sampleWithPartialData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.partialUpdate(patchObject).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PATCH' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should return a list of CategoriePointDeVente', () => {
      const returnedFromService = { ...requireRestSample };

      const expected = { ...sampleWithRequiredData };

      service.query().subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'GET' });
      req.flush([returnedFromService]);
      httpMock.verify();
      expect(expectedResult).toMatchObject([expected]);
    });

    it('should delete a CategoriePointDeVente', () => {
      const expected = true;

      service.delete(123).subscribe(resp => (expectedResult = resp.ok));

      const req = httpMock.expectOne({ method: 'DELETE' });
      req.flush({ status: 200 });
      expect(expectedResult).toBe(expected);
    });

    describe('addCategoriePointDeVenteToCollectionIfMissing', () => {
      it('should add a CategoriePointDeVente to an empty array', () => {
        const categoriePointDeVente: ICategoriePointDeVente = sampleWithRequiredData;
        expectedResult = service.addCategoriePointDeVenteToCollectionIfMissing([], categoriePointDeVente);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(categoriePointDeVente);
      });

      it('should not add a CategoriePointDeVente to an array that contains it', () => {
        const categoriePointDeVente: ICategoriePointDeVente = sampleWithRequiredData;
        const categoriePointDeVenteCollection: ICategoriePointDeVente[] = [
          {
            ...categoriePointDeVente,
          },
          sampleWithPartialData,
        ];
        expectedResult = service.addCategoriePointDeVenteToCollectionIfMissing(categoriePointDeVenteCollection, categoriePointDeVente);
        expect(expectedResult).toHaveLength(2);
      });

      it("should add a CategoriePointDeVente to an array that doesn't contain it", () => {
        const categoriePointDeVente: ICategoriePointDeVente = sampleWithRequiredData;
        const categoriePointDeVenteCollection: ICategoriePointDeVente[] = [sampleWithPartialData];
        expectedResult = service.addCategoriePointDeVenteToCollectionIfMissing(categoriePointDeVenteCollection, categoriePointDeVente);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(categoriePointDeVente);
      });

      it('should add only unique CategoriePointDeVente to an array', () => {
        const categoriePointDeVenteArray: ICategoriePointDeVente[] = [sampleWithRequiredData, sampleWithPartialData, sampleWithFullData];
        const categoriePointDeVenteCollection: ICategoriePointDeVente[] = [sampleWithRequiredData];
        expectedResult = service.addCategoriePointDeVenteToCollectionIfMissing(
          categoriePointDeVenteCollection,
          ...categoriePointDeVenteArray,
        );
        expect(expectedResult).toHaveLength(3);
      });

      it('should accept varargs', () => {
        const categoriePointDeVente: ICategoriePointDeVente = sampleWithRequiredData;
        const categoriePointDeVente2: ICategoriePointDeVente = sampleWithPartialData;
        expectedResult = service.addCategoriePointDeVenteToCollectionIfMissing([], categoriePointDeVente, categoriePointDeVente2);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(categoriePointDeVente);
        expect(expectedResult).toContain(categoriePointDeVente2);
      });

      it('should accept null and undefined values', () => {
        const categoriePointDeVente: ICategoriePointDeVente = sampleWithRequiredData;
        expectedResult = service.addCategoriePointDeVenteToCollectionIfMissing([], null, categoriePointDeVente, undefined);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(categoriePointDeVente);
      });

      it('should return initial array if no CategoriePointDeVente is added', () => {
        const categoriePointDeVenteCollection: ICategoriePointDeVente[] = [sampleWithRequiredData];
        expectedResult = service.addCategoriePointDeVenteToCollectionIfMissing(categoriePointDeVenteCollection, undefined, null);
        expect(expectedResult).toEqual(categoriePointDeVenteCollection);
      });
    });

    describe('compareCategoriePointDeVente', () => {
      it('should return true if both entities are null', () => {
        const entity1 = null;
        const entity2 = null;

        const compareResult = service.compareCategoriePointDeVente(entity1, entity2);

        expect(compareResult).toEqual(true);
      });

      it('should return false if one entity is null', () => {
        const entity1 = { id: 6431 };
        const entity2 = null;

        const compareResult1 = service.compareCategoriePointDeVente(entity1, entity2);
        const compareResult2 = service.compareCategoriePointDeVente(entity2, entity1);

        expect(compareResult1).toEqual(false);
        expect(compareResult2).toEqual(false);
      });

      it('should return false if primaryKey differs', () => {
        const entity1 = { id: 6431 };
        const entity2 = { id: 11623 };

        const compareResult1 = service.compareCategoriePointDeVente(entity1, entity2);
        const compareResult2 = service.compareCategoriePointDeVente(entity2, entity1);

        expect(compareResult1).toEqual(false);
        expect(compareResult2).toEqual(false);
      });

      it('should return false if primaryKey matches', () => {
        const entity1 = { id: 6431 };
        const entity2 = { id: 6431 };

        const compareResult1 = service.compareCategoriePointDeVente(entity1, entity2);
        const compareResult2 = service.compareCategoriePointDeVente(entity2, entity1);

        expect(compareResult1).toEqual(true);
        expect(compareResult2).toEqual(true);
      });
    });
  });

  afterEach(() => {
    httpMock.verify();
  });
});
