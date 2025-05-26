import { inject } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRouteSnapshot, Router } from '@angular/router';
import { EMPTY, Observable, of } from 'rxjs';
import { mergeMap } from 'rxjs/operators';

import { IHier } from '../hier.model';
import { HierService } from '../service/hier.service';

const hierResolve = (route: ActivatedRouteSnapshot): Observable<null | IHier> => {
  const id = route.params.id;
  if (id) {
    return inject(HierService)
      .find(id)
      .pipe(
        mergeMap((hier: HttpResponse<IHier>) => {
          if (hier.body) {
            return of(hier.body);
          }
          inject(Router).navigate(['404']);
          return EMPTY;
        }),
      );
  }
  return of(null);
};

export default hierResolve;
