import { inject } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRouteSnapshot, Router } from '@angular/router';
import { EMPTY, Observable, of } from 'rxjs';
import { mergeMap } from 'rxjs/operators';

import { IBarcode } from '../barcode.model';
import { BarcodeService } from '../service/barcode.service';

const barcodeResolve = (route: ActivatedRouteSnapshot): Observable<null | IBarcode> => {
  const id = route.params.id;
  if (id) {
    return inject(BarcodeService)
      .find(id)
      .pipe(
        mergeMap((barcode: HttpResponse<IBarcode>) => {
          if (barcode.body) {
            return of(barcode.body);
          }
          inject(Router).navigate(['404']);
          return EMPTY;
        }),
      );
  }
  return of(null);
};

export default barcodeResolve;
