import { inject } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRouteSnapshot, Router } from '@angular/router';
import { EMPTY, Observable, of } from 'rxjs';
import { mergeMap } from 'rxjs/operators';

import { ITaxe } from '../taxe.model';
import { TaxeService } from '../service/taxe.service';

const taxeResolve = (route: ActivatedRouteSnapshot): Observable<null | ITaxe> => {
  const id = route.params.id;
  if (id) {
    return inject(TaxeService)
      .find(id)
      .pipe(
        mergeMap((taxe: HttpResponse<ITaxe>) => {
          if (taxe.body) {
            return of(taxe.body);
          }
          inject(Router).navigate(['404']);
          return EMPTY;
        }),
      );
  }
  return of(null);
};

export default taxeResolve;
