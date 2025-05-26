import { inject } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRouteSnapshot, Router } from '@angular/router';
import { EMPTY, Observable, of } from 'rxjs';
import { mergeMap } from 'rxjs/operators';

import { IFactureDetail } from '../facture-detail.model';
import { FactureDetailService } from '../service/facture-detail.service';

const factureDetailResolve = (route: ActivatedRouteSnapshot): Observable<null | IFactureDetail> => {
  const id = route.params.id;
  if (id) {
    return inject(FactureDetailService)
      .find(id)
      .pipe(
        mergeMap((factureDetail: HttpResponse<IFactureDetail>) => {
          if (factureDetail.body) {
            return of(factureDetail.body);
          }
          inject(Router).navigate(['404']);
          return EMPTY;
        }),
      );
  }
  return of(null);
};

export default factureDetailResolve;
