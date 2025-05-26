import { inject } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRouteSnapshot, Router } from '@angular/router';
import { EMPTY, Observable, of } from 'rxjs';
import { mergeMap } from 'rxjs/operators';

import { IMajorGroup } from '../major-group.model';
import { MajorGroupService } from '../service/major-group.service';

const majorGroupResolve = (route: ActivatedRouteSnapshot): Observable<null | IMajorGroup> => {
  const id = route.params.id;
  if (id) {
    return inject(MajorGroupService)
      .find(id)
      .pipe(
        mergeMap((majorGroup: HttpResponse<IMajorGroup>) => {
          if (majorGroup.body) {
            return of(majorGroup.body);
          }
          inject(Router).navigate(['404']);
          return EMPTY;
        }),
      );
  }
  return of(null);
};

export default majorGroupResolve;
