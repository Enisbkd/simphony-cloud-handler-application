import { inject } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRouteSnapshot, Router } from '@angular/router';
import { EMPTY, Observable, of } from 'rxjs';
import { mergeMap } from 'rxjs/operators';

import { IElementMenu } from '../element-menu.model';
import { ElementMenuService } from '../service/element-menu.service';

const elementMenuResolve = (route: ActivatedRouteSnapshot): Observable<null | IElementMenu> => {
  const id = route.params.id;
  if (id) {
    return inject(ElementMenuService)
      .find(id)
      .pipe(
        mergeMap((elementMenu: HttpResponse<IElementMenu>) => {
          if (elementMenu.body) {
            return of(elementMenu.body);
          }
          inject(Router).navigate(['404']);
          return EMPTY;
        }),
      );
  }
  return of(null);
};

export default elementMenuResolve;
