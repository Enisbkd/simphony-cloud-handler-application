import { inject } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRouteSnapshot, Router } from '@angular/router';
import { EMPTY, Observable, of } from 'rxjs';
import { mergeMap } from 'rxjs/operators';

import { IRemise } from '../remise.model';
import { RemiseService } from '../service/remise.service';

const remiseResolve = (route: ActivatedRouteSnapshot): Observable<null | IRemise> => {
  const id = route.params.id;
  if (id) {
    return inject(RemiseService)
      .find(id)
      .pipe(
        mergeMap((remise: HttpResponse<IRemise>) => {
          if (remise.body) {
            return of(remise.body);
          }
          inject(Router).navigate(['404']);
          return EMPTY;
        }),
      );
  }
  return of(null);
};

export default remiseResolve;
