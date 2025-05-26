import { inject } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRouteSnapshot, Router } from '@angular/router';
import { EMPTY, Observable, of } from 'rxjs';
import { mergeMap } from 'rxjs/operators';

import { ICodeRaison } from '../code-raison.model';
import { CodeRaisonService } from '../service/code-raison.service';

const codeRaisonResolve = (route: ActivatedRouteSnapshot): Observable<null | ICodeRaison> => {
  const id = route.params.id;
  if (id) {
    return inject(CodeRaisonService)
      .find(id)
      .pipe(
        mergeMap((codeRaison: HttpResponse<ICodeRaison>) => {
          if (codeRaison.body) {
            return of(codeRaison.body);
          }
          inject(Router).navigate(['404']);
          return EMPTY;
        }),
      );
  }
  return of(null);
};

export default codeRaisonResolve;
