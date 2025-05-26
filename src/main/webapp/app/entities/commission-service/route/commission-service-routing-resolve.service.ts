import { inject } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRouteSnapshot, Router } from '@angular/router';
import { EMPTY, Observable, of } from 'rxjs';
import { mergeMap } from 'rxjs/operators';

import { ICommissionService } from '../commission-service.model';
import { CommissionServiceService } from '../service/commission-service.service';

const commissionServiceResolve = (route: ActivatedRouteSnapshot): Observable<null | ICommissionService> => {
  const id = route.params.id;
  if (id) {
    return inject(CommissionServiceService)
      .find(id)
      .pipe(
        mergeMap((commissionService: HttpResponse<ICommissionService>) => {
          if (commissionService.body) {
            return of(commissionService.body);
          }
          inject(Router).navigate(['404']);
          return EMPTY;
        }),
      );
  }
  return of(null);
};

export default commissionServiceResolve;
