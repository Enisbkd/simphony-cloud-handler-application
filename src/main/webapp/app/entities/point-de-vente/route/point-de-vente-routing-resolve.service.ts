import { inject } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRouteSnapshot, Router } from '@angular/router';
import { EMPTY, Observable, of } from 'rxjs';
import { mergeMap } from 'rxjs/operators';

import { IPointDeVente } from '../point-de-vente.model';
import { PointDeVenteService } from '../service/point-de-vente.service';

const pointDeVenteResolve = (route: ActivatedRouteSnapshot): Observable<null | IPointDeVente> => {
  const id = route.params.id;
  if (id) {
    return inject(PointDeVenteService)
      .find(id)
      .pipe(
        mergeMap((pointDeVente: HttpResponse<IPointDeVente>) => {
          if (pointDeVente.body) {
            return of(pointDeVente.body);
          }
          inject(Router).navigate(['404']);
          return EMPTY;
        }),
      );
  }
  return of(null);
};

export default pointDeVenteResolve;
