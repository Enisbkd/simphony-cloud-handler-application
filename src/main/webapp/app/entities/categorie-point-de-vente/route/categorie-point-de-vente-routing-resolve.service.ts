import { inject } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRouteSnapshot, Router } from '@angular/router';
import { EMPTY, Observable, of } from 'rxjs';
import { mergeMap } from 'rxjs/operators';

import { ICategoriePointDeVente } from '../categorie-point-de-vente.model';
import { CategoriePointDeVenteService } from '../service/categorie-point-de-vente.service';

const categoriePointDeVenteResolve = (route: ActivatedRouteSnapshot): Observable<null | ICategoriePointDeVente> => {
  const id = route.params.id;
  if (id) {
    return inject(CategoriePointDeVenteService)
      .find(id)
      .pipe(
        mergeMap((categoriePointDeVente: HttpResponse<ICategoriePointDeVente>) => {
          if (categoriePointDeVente.body) {
            return of(categoriePointDeVente.body);
          }
          inject(Router).navigate(['404']);
          return EMPTY;
        }),
      );
  }
  return of(null);
};

export default categoriePointDeVenteResolve;
