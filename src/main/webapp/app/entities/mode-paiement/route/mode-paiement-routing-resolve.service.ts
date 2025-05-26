import { inject } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRouteSnapshot, Router } from '@angular/router';
import { EMPTY, Observable, of } from 'rxjs';
import { mergeMap } from 'rxjs/operators';

import { IModePaiement } from '../mode-paiement.model';
import { ModePaiementService } from '../service/mode-paiement.service';

const modePaiementResolve = (route: ActivatedRouteSnapshot): Observable<null | IModePaiement> => {
  const id = route.params.id;
  if (id) {
    return inject(ModePaiementService)
      .find(id)
      .pipe(
        mergeMap((modePaiement: HttpResponse<IModePaiement>) => {
          if (modePaiement.body) {
            return of(modePaiement.body);
          }
          inject(Router).navigate(['404']);
          return EMPTY;
        }),
      );
  }
  return of(null);
};

export default modePaiementResolve;
