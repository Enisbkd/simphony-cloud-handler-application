import { inject } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRouteSnapshot, Router } from '@angular/router';
import { EMPTY, Observable, of } from 'rxjs';
import { mergeMap } from 'rxjs/operators';

import { IFamilyGroup } from '../family-group.model';
import { FamilyGroupService } from '../service/family-group.service';

const familyGroupResolve = (route: ActivatedRouteSnapshot): Observable<null | IFamilyGroup> => {
  const id = route.params.id;
  if (id) {
    return inject(FamilyGroupService)
      .find(id)
      .pipe(
        mergeMap((familyGroup: HttpResponse<IFamilyGroup>) => {
          if (familyGroup.body) {
            return of(familyGroup.body);
          }
          inject(Router).navigate(['404']);
          return EMPTY;
        }),
      );
  }
  return of(null);
};

export default familyGroupResolve;
