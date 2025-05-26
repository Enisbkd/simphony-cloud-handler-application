import { inject } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRouteSnapshot, Router } from '@angular/router';
import { EMPTY, Observable, of } from 'rxjs';
import { mergeMap } from 'rxjs/operators';

import { IPartieDeJournee } from '../partie-de-journee.model';
import { PartieDeJourneeService } from '../service/partie-de-journee.service';

const partieDeJourneeResolve = (route: ActivatedRouteSnapshot): Observable<null | IPartieDeJournee> => {
  const id = route.params.id;
  if (id) {
    return inject(PartieDeJourneeService)
      .find(id)
      .pipe(
        mergeMap((partieDeJournee: HttpResponse<IPartieDeJournee>) => {
          if (partieDeJournee.body) {
            return of(partieDeJournee.body);
          }
          inject(Router).navigate(['404']);
          return EMPTY;
        }),
      );
  }
  return of(null);
};

export default partieDeJourneeResolve;
