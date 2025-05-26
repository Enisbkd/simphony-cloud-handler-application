import { TestBed } from '@angular/core/testing';
import { HttpTestingController, provideHttpClientTesting } from '@angular/common/http/testing';
import { provideHttpClient } from '@angular/common/http';

import { IElementMenu } from '../element-menu.model';
import { sampleWithFullData, sampleWithNewData, sampleWithPartialData, sampleWithRequiredData } from '../element-menu.test-samples';

import { ElementMenuService } from './element-menu.service';

const requireRestSample: IElementMenu = {
  ...sampleWithRequiredData,
};

describe('ElementMenu Service', () => {
  let service: ElementMenuService;
  let httpMock: HttpTestingController;
  let expectedResult: IElementMenu | IElementMenu[] | boolean | null;

  beforeEach(() => {
    TestBed.configureTestingModule({
      providers: [provideHttpClient(), provideHttpClientTesting()],
    });
    expectedResult = null;
    service = TestBed.inject(ElementMenuService);
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

    it('should create a ElementMenu', () => {
      const elementMenu = { ...sampleWithNewData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.create(elementMenu).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'POST' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should update a ElementMenu', () => {
      const elementMenu = { ...sampleWithRequiredData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.update(elementMenu).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PUT' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should partial update a ElementMenu', () => {
      const patchObject = { ...sampleWithPartialData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.partialUpdate(patchObject).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PATCH' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should return a list of ElementMenu', () => {
      const returnedFromService = { ...requireRestSample };

      const expected = { ...sampleWithRequiredData };

      service.query().subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'GET' });
      req.flush([returnedFromService]);
      httpMock.verify();
      expect(expectedResult).toMatchObject([expected]);
    });

    it('should delete a ElementMenu', () => {
      const expected = true;

      service.delete(123).subscribe(resp => (expectedResult = resp.ok));

      const req = httpMock.expectOne({ method: 'DELETE' });
      req.flush({ status: 200 });
      expect(expectedResult).toBe(expected);
    });

    describe('addElementMenuToCollectionIfMissing', () => {
      it('should add a ElementMenu to an empty array', () => {
        const elementMenu: IElementMenu = sampleWithRequiredData;
        expectedResult = service.addElementMenuToCollectionIfMissing([], elementMenu);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(elementMenu);
      });

      it('should not add a ElementMenu to an array that contains it', () => {
        const elementMenu: IElementMenu = sampleWithRequiredData;
        const elementMenuCollection: IElementMenu[] = [
          {
            ...elementMenu,
          },
          sampleWithPartialData,
        ];
        expectedResult = service.addElementMenuToCollectionIfMissing(elementMenuCollection, elementMenu);
        expect(expectedResult).toHaveLength(2);
      });

      it("should add a ElementMenu to an array that doesn't contain it", () => {
        const elementMenu: IElementMenu = sampleWithRequiredData;
        const elementMenuCollection: IElementMenu[] = [sampleWithPartialData];
        expectedResult = service.addElementMenuToCollectionIfMissing(elementMenuCollection, elementMenu);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(elementMenu);
      });

      it('should add only unique ElementMenu to an array', () => {
        const elementMenuArray: IElementMenu[] = [sampleWithRequiredData, sampleWithPartialData, sampleWithFullData];
        const elementMenuCollection: IElementMenu[] = [sampleWithRequiredData];
        expectedResult = service.addElementMenuToCollectionIfMissing(elementMenuCollection, ...elementMenuArray);
        expect(expectedResult).toHaveLength(3);
      });

      it('should accept varargs', () => {
        const elementMenu: IElementMenu = sampleWithRequiredData;
        const elementMenu2: IElementMenu = sampleWithPartialData;
        expectedResult = service.addElementMenuToCollectionIfMissing([], elementMenu, elementMenu2);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(elementMenu);
        expect(expectedResult).toContain(elementMenu2);
      });

      it('should accept null and undefined values', () => {
        const elementMenu: IElementMenu = sampleWithRequiredData;
        expectedResult = service.addElementMenuToCollectionIfMissing([], null, elementMenu, undefined);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(elementMenu);
      });

      it('should return initial array if no ElementMenu is added', () => {
        const elementMenuCollection: IElementMenu[] = [sampleWithRequiredData];
        expectedResult = service.addElementMenuToCollectionIfMissing(elementMenuCollection, undefined, null);
        expect(expectedResult).toEqual(elementMenuCollection);
      });
    });

    describe('compareElementMenu', () => {
      it('should return true if both entities are null', () => {
        const entity1 = null;
        const entity2 = null;

        const compareResult = service.compareElementMenu(entity1, entity2);

        expect(compareResult).toEqual(true);
      });

      it('should return false if one entity is null', () => {
        const entity1 = { id: 25075 };
        const entity2 = null;

        const compareResult1 = service.compareElementMenu(entity1, entity2);
        const compareResult2 = service.compareElementMenu(entity2, entity1);

        expect(compareResult1).toEqual(false);
        expect(compareResult2).toEqual(false);
      });

      it('should return false if primaryKey differs', () => {
        const entity1 = { id: 25075 };
        const entity2 = { id: 1836 };

        const compareResult1 = service.compareElementMenu(entity1, entity2);
        const compareResult2 = service.compareElementMenu(entity2, entity1);

        expect(compareResult1).toEqual(false);
        expect(compareResult2).toEqual(false);
      });

      it('should return false if primaryKey matches', () => {
        const entity1 = { id: 25075 };
        const entity2 = { id: 25075 };

        const compareResult1 = service.compareElementMenu(entity1, entity2);
        const compareResult2 = service.compareElementMenu(entity2, entity1);

        expect(compareResult1).toEqual(true);
        expect(compareResult2).toEqual(true);
      });
    });
  });

  afterEach(() => {
    httpMock.verify();
  });
});
