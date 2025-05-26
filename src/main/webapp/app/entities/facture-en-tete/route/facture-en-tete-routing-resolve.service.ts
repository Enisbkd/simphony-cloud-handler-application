import { inject } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRouteSnapshot, Router } from '@angular/router';
import { EMPTY, Observable, of } from 'rxjs';
import { mergeMap } from 'rxjs/operators';

import { IFactureEnTete } from '../facture-en-tete.model';
import { FactureEnTeteService } from '../service/facture-en-tete.service';

const factureEnTeteResolve = (route: ActivatedRouteSnapshot): Observable<null | IFactureEnTete> => {
  const id = route.params.id;
  if (id) {
    return inject(FactureEnTeteService)
      .find(id)
      .pipe(
        mergeMap((factureEnTete: HttpResponse<IFactureEnTete>) => {
          if (factureEnTete.body) {
            return of(factureEnTete.body);
          }
          inject(Router).navigate(['404']);
          return EMPTY;
        }),
      );
  }
  return of(null);
};

export default factureEnTeteResolve;
