import { TestBed } from '@angular/core/testing';
import { HttpTestingController, provideHttpClientTesting } from '@angular/common/http/testing';
import { provideHttpClient } from '@angular/common/http';

import { IPointDeVente } from '../point-de-vente.model';
import { sampleWithFullData, sampleWithNewData, sampleWithPartialData, sampleWithRequiredData } from '../point-de-vente.test-samples';

import { PointDeVenteService } from './point-de-vente.service';

const requireRestSample: IPointDeVente = {
  ...sampleWithRequiredData,
};

describe('PointDeVente Service', () => {
  let service: PointDeVenteService;
  let httpMock: HttpTestingController;
  let expectedResult: IPointDeVente | IPointDeVente[] | boolean | null;

  beforeEach(() => {
    TestBed.configureTestingModule({
      providers: [provideHttpClient(), provideHttpClientTesting()],
    });
    expectedResult = null;
    service = TestBed.inject(PointDeVenteService);
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

    it('should create a PointDeVente', () => {
      const pointDeVente = { ...sampleWithNewData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.create(pointDeVente).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'POST' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should update a PointDeVente', () => {
      const pointDeVente = { ...sampleWithRequiredData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.update(pointDeVente).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PUT' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should partial update a PointDeVente', () => {
      const patchObject = { ...sampleWithPartialData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.partialUpdate(patchObject).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PATCH' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should return a list of PointDeVente', () => {
      const returnedFromService = { ...requireRestSample };

      const expected = { ...sampleWithRequiredData };

      service.query().subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'GET' });
      req.flush([returnedFromService]);
      httpMock.verify();
      expect(expectedResult).toMatchObject([expected]);
    });

    it('should delete a PointDeVente', () => {
      const expected = true;

      service.delete(123).subscribe(resp => (expectedResult = resp.ok));

      const req = httpMock.expectOne({ method: 'DELETE' });
      req.flush({ status: 200 });
      expect(expectedResult).toBe(expected);
    });

    describe('addPointDeVenteToCollectionIfMissing', () => {
      it('should add a PointDeVente to an empty array', () => {
        const pointDeVente: IPointDeVente = sampleWithRequiredData;
        expectedResult = service.addPointDeVenteToCollectionIfMissing([], pointDeVente);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(pointDeVente);
      });

      it('should not add a PointDeVente to an array that contains it', () => {
        const pointDeVente: IPointDeVente = sampleWithRequiredData;
        const pointDeVenteCollection: IPointDeVente[] = [
          {
            ...pointDeVente,
          },
          sampleWithPartialData,
        ];
        expectedResult = service.addPointDeVenteToCollectionIfMissing(pointDeVenteCollection, pointDeVente);
        expect(expectedResult).toHaveLength(2);
      });

      it("should add a PointDeVente to an array that doesn't contain it", () => {
        const pointDeVente: IPointDeVente = sampleWithRequiredData;
        const pointDeVenteCollection: IPointDeVente[] = [sampleWithPartialData];
        expectedResult = service.addPointDeVenteToCollectionIfMissing(pointDeVenteCollection, pointDeVente);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(pointDeVente);
      });

      it('should add only unique PointDeVente to an array', () => {
        const pointDeVenteArray: IPointDeVente[] = [sampleWithRequiredData, sampleWithPartialData, sampleWithFullData];
        const pointDeVenteCollection: IPointDeVente[] = [sampleWithRequiredData];
        expectedResult = service.addPointDeVenteToCollectionIfMissing(pointDeVenteCollection, ...pointDeVenteArray);
        expect(expectedResult).toHaveLength(3);
      });

      it('should accept varargs', () => {
        const pointDeVente: IPointDeVente = sampleWithRequiredData;
        const pointDeVente2: IPointDeVente = sampleWithPartialData;
        expectedResult = service.addPointDeVenteToCollectionIfMissing([], pointDeVente, pointDeVente2);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(pointDeVente);
        expect(expectedResult).toContain(pointDeVente2);
      });

      it('should accept null and undefined values', () => {
        const pointDeVente: IPointDeVente = sampleWithRequiredData;
        expectedResult = service.addPointDeVenteToCollectionIfMissing([], null, pointDeVente, undefined);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(pointDeVente);
      });

      it('should return initial array if no PointDeVente is added', () => {
        const pointDeVenteCollection: IPointDeVente[] = [sampleWithRequiredData];
        expectedResult = service.addPointDeVenteToCollectionIfMissing(pointDeVenteCollection, undefined, null);
        expect(expectedResult).toEqual(pointDeVenteCollection);
      });
    });

    describe('comparePointDeVente', () => {
      it('should return true if both entities are null', () => {
        const entity1 = null;
        const entity2 = null;

        const compareResult = service.comparePointDeVente(entity1, entity2);

        expect(compareResult).toEqual(true);
      });

      it('should return false if one entity is null', () => {
        const entity1 = { id: 30602 };
        const entity2 = null;

        const compareResult1 = service.comparePointDeVente(entity1, entity2);
        const compareResult2 = service.comparePointDeVente(entity2, entity1);

        expect(compareResult1).toEqual(false);
        expect(compareResult2).toEqual(false);
      });

      it('should return false if primaryKey differs', () => {
        const entity1 = { id: 30602 };
        const entity2 = { id: 15959 };

        const compareResult1 = service.comparePointDeVente(entity1, entity2);
        const compareResult2 = service.comparePointDeVente(entity2, entity1);

        expect(compareResult1).toEqual(false);
        expect(compareResult2).toEqual(false);
      });

      it('should return false if primaryKey matches', () => {
        const entity1 = { id: 30602 };
        const entity2 = { id: 30602 };

        const compareResult1 = service.comparePointDeVente(entity1, entity2);
        const compareResult2 = service.comparePointDeVente(entity2, entity1);

        expect(compareResult1).toEqual(true);
        expect(compareResult2).toEqual(true);
      });
    });
  });

  afterEach(() => {
    httpMock.verify();
  });
});
