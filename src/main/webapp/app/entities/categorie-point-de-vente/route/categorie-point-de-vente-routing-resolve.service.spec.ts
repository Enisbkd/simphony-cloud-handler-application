import { TestBed } from '@angular/core/testing';
import { HttpResponse, provideHttpClient } from '@angular/common/http';
import { ActivatedRoute, ActivatedRouteSnapshot, Router, convertToParamMap } from '@angular/router';
import { of } from 'rxjs';

import { ICategoriePointDeVente } from '../categorie-point-de-vente.model';
import { CategoriePointDeVenteService } from '../service/categorie-point-de-vente.service';

import categoriePointDeVenteResolve from './categorie-point-de-vente-routing-resolve.service';

describe('CategoriePointDeVente routing resolve service', () => {
  let mockRouter: Router;
  let mockActivatedRouteSnapshot: ActivatedRouteSnapshot;
  let service: CategoriePointDeVenteService;
  let resultCategoriePointDeVente: ICategoriePointDeVente | null | undefined;

  beforeEach(() => {
    TestBed.configureTestingModule({
      providers: [
        provideHttpClient(),
        {
          provide: ActivatedRoute,
          useValue: {
            snapshot: {
              paramMap: convertToParamMap({}),
            },
          },
        },
      ],
    });
    mockRouter = TestBed.inject(Router);
    jest.spyOn(mockRouter, 'navigate').mockImplementation(() => Promise.resolve(true));
    mockActivatedRouteSnapshot = TestBed.inject(ActivatedRoute).snapshot;
    service = TestBed.inject(CategoriePointDeVenteService);
    resultCategoriePointDeVente = undefined;
  });

  describe('resolve', () => {
    it('should return ICategoriePointDeVente returned by find', () => {
      // GIVEN
      service.find = jest.fn(id => of(new HttpResponse({ body: { id } })));
      mockActivatedRouteSnapshot.params = { id: 123 };

      // WHEN
      TestBed.runInInjectionContext(() => {
        categoriePointDeVenteResolve(mockActivatedRouteSnapshot).subscribe({
          next(result) {
            resultCategoriePointDeVente = result;
          },
        });
      });

      // THEN
      expect(service.find).toHaveBeenCalledWith(123);
      expect(resultCategoriePointDeVente).toEqual({ id: 123 });
    });

    it('should return null if id is not provided', () => {
      // GIVEN
      service.find = jest.fn();
      mockActivatedRouteSnapshot.params = {};

      // WHEN
      TestBed.runInInjectionContext(() => {
        categoriePointDeVenteResolve(mockActivatedRouteSnapshot).subscribe({
          next(result) {
            resultCategoriePointDeVente = result;
          },
        });
      });

      // THEN
      expect(service.find).not.toHaveBeenCalled();
      expect(resultCategoriePointDeVente).toEqual(null);
    });

    it('should route to 404 page if data not found in server', () => {
      // GIVEN
      jest.spyOn(service, 'find').mockReturnValue(of(new HttpResponse<ICategoriePointDeVente>({ body: null })));
      mockActivatedRouteSnapshot.params = { id: 123 };

      // WHEN
      TestBed.runInInjectionContext(() => {
        categoriePointDeVenteResolve(mockActivatedRouteSnapshot).subscribe({
          next(result) {
            resultCategoriePointDeVente = result;
          },
        });
      });

      // THEN
      expect(service.find).toHaveBeenCalledWith(123);
      expect(resultCategoriePointDeVente).toEqual(undefined);
      expect(mockRouter.navigate).toHaveBeenCalledWith(['404']);
    });
  });
});
