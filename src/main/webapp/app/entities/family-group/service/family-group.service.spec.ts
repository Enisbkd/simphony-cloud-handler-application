import { TestBed } from '@angular/core/testing';
import { HttpTestingController, provideHttpClientTesting } from '@angular/common/http/testing';
import { provideHttpClient } from '@angular/common/http';

import { IFamilyGroup } from '../family-group.model';
import { sampleWithFullData, sampleWithNewData, sampleWithPartialData, sampleWithRequiredData } from '../family-group.test-samples';

import { FamilyGroupService } from './family-group.service';

const requireRestSample: IFamilyGroup = {
  ...sampleWithRequiredData,
};

describe('FamilyGroup Service', () => {
  let service: FamilyGroupService;
  let httpMock: HttpTestingController;
  let expectedResult: IFamilyGroup | IFamilyGroup[] | boolean | null;

  beforeEach(() => {
    TestBed.configureTestingModule({
      providers: [provideHttpClient(), provideHttpClientTesting()],
    });
    expectedResult = null;
    service = TestBed.inject(FamilyGroupService);
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

    it('should create a FamilyGroup', () => {
      const familyGroup = { ...sampleWithNewData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.create(familyGroup).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'POST' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should update a FamilyGroup', () => {
      const familyGroup = { ...sampleWithRequiredData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.update(familyGroup).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PUT' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should partial update a FamilyGroup', () => {
      const patchObject = { ...sampleWithPartialData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.partialUpdate(patchObject).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PATCH' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should return a list of FamilyGroup', () => {
      const returnedFromService = { ...requireRestSample };

      const expected = { ...sampleWithRequiredData };

      service.query().subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'GET' });
      req.flush([returnedFromService]);
      httpMock.verify();
      expect(expectedResult).toMatchObject([expected]);
    });

    it('should delete a FamilyGroup', () => {
      const expected = true;

      service.delete(123).subscribe(resp => (expectedResult = resp.ok));

      const req = httpMock.expectOne({ method: 'DELETE' });
      req.flush({ status: 200 });
      expect(expectedResult).toBe(expected);
    });

    describe('addFamilyGroupToCollectionIfMissing', () => {
      it('should add a FamilyGroup to an empty array', () => {
        const familyGroup: IFamilyGroup = sampleWithRequiredData;
        expectedResult = service.addFamilyGroupToCollectionIfMissing([], familyGroup);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(familyGroup);
      });

      it('should not add a FamilyGroup to an array that contains it', () => {
        const familyGroup: IFamilyGroup = sampleWithRequiredData;
        const familyGroupCollection: IFamilyGroup[] = [
          {
            ...familyGroup,
          },
          sampleWithPartialData,
        ];
        expectedResult = service.addFamilyGroupToCollectionIfMissing(familyGroupCollection, familyGroup);
        expect(expectedResult).toHaveLength(2);
      });

      it("should add a FamilyGroup to an array that doesn't contain it", () => {
        const familyGroup: IFamilyGroup = sampleWithRequiredData;
        const familyGroupCollection: IFamilyGroup[] = [sampleWithPartialData];
        expectedResult = service.addFamilyGroupToCollectionIfMissing(familyGroupCollection, familyGroup);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(familyGroup);
      });

      it('should add only unique FamilyGroup to an array', () => {
        const familyGroupArray: IFamilyGroup[] = [sampleWithRequiredData, sampleWithPartialData, sampleWithFullData];
        const familyGroupCollection: IFamilyGroup[] = [sampleWithRequiredData];
        expectedResult = service.addFamilyGroupToCollectionIfMissing(familyGroupCollection, ...familyGroupArray);
        expect(expectedResult).toHaveLength(3);
      });

      it('should accept varargs', () => {
        const familyGroup: IFamilyGroup = sampleWithRequiredData;
        const familyGroup2: IFamilyGroup = sampleWithPartialData;
        expectedResult = service.addFamilyGroupToCollectionIfMissing([], familyGroup, familyGroup2);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(familyGroup);
        expect(expectedResult).toContain(familyGroup2);
      });

      it('should accept null and undefined values', () => {
        const familyGroup: IFamilyGroup = sampleWithRequiredData;
        expectedResult = service.addFamilyGroupToCollectionIfMissing([], null, familyGroup, undefined);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(familyGroup);
      });

      it('should return initial array if no FamilyGroup is added', () => {
        const familyGroupCollection: IFamilyGroup[] = [sampleWithRequiredData];
        expectedResult = service.addFamilyGroupToCollectionIfMissing(familyGroupCollection, undefined, null);
        expect(expectedResult).toEqual(familyGroupCollection);
      });
    });

    describe('compareFamilyGroup', () => {
      it('should return true if both entities are null', () => {
        const entity1 = null;
        const entity2 = null;

        const compareResult = service.compareFamilyGroup(entity1, entity2);

        expect(compareResult).toEqual(true);
      });

      it('should return false if one entity is null', () => {
        const entity1 = { id: 28164 };
        const entity2 = null;

        const compareResult1 = service.compareFamilyGroup(entity1, entity2);
        const compareResult2 = service.compareFamilyGroup(entity2, entity1);

        expect(compareResult1).toEqual(false);
        expect(compareResult2).toEqual(false);
      });

      it('should return false if primaryKey differs', () => {
        const entity1 = { id: 28164 };
        const entity2 = { id: 19289 };

        const compareResult1 = service.compareFamilyGroup(entity1, entity2);
        const compareResult2 = service.compareFamilyGroup(entity2, entity1);

        expect(compareResult1).toEqual(false);
        expect(compareResult2).toEqual(false);
      });

      it('should return false if primaryKey matches', () => {
        const entity1 = { id: 28164 };
        const entity2 = { id: 28164 };

        const compareResult1 = service.compareFamilyGroup(entity1, entity2);
        const compareResult2 = service.compareFamilyGroup(entity2, entity1);

        expect(compareResult1).toEqual(true);
        expect(compareResult2).toEqual(true);
      });
    });
  });

  afterEach(() => {
    httpMock.verify();
  });
});
