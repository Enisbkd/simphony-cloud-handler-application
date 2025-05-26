import { Injectable, inject } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { isPresent } from 'app/core/util/operators';
import { ApplicationConfigService } from 'app/core/config/application-config.service';
import { createRequestOption } from 'app/core/request/request-util';
import { IBarcode, NewBarcode } from '../barcode.model';

export type PartialUpdateBarcode = Partial<IBarcode> & Pick<IBarcode, 'id'>;

export type EntityResponseType = HttpResponse<IBarcode>;
export type EntityArrayResponseType = HttpResponse<IBarcode[]>;

@Injectable({ providedIn: 'root' })
export class BarcodeService {
  protected readonly http = inject(HttpClient);
  protected readonly applicationConfigService = inject(ApplicationConfigService);

  protected resourceUrl = this.applicationConfigService.getEndpointFor('api/barcodes');

  create(barcode: NewBarcode): Observable<EntityResponseType> {
    return this.http.post<IBarcode>(this.resourceUrl, barcode, { observe: 'response' });
  }

  update(barcode: IBarcode): Observable<EntityResponseType> {
    return this.http.put<IBarcode>(`${this.resourceUrl}/${this.getBarcodeIdentifier(barcode)}`, barcode, { observe: 'response' });
  }

  partialUpdate(barcode: PartialUpdateBarcode): Observable<EntityResponseType> {
    return this.http.patch<IBarcode>(`${this.resourceUrl}/${this.getBarcodeIdentifier(barcode)}`, barcode, { observe: 'response' });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<IBarcode>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IBarcode[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  getBarcodeIdentifier(barcode: Pick<IBarcode, 'id'>): number {
    return barcode.id;
  }

  compareBarcode(o1: Pick<IBarcode, 'id'> | null, o2: Pick<IBarcode, 'id'> | null): boolean {
    return o1 && o2 ? this.getBarcodeIdentifier(o1) === this.getBarcodeIdentifier(o2) : o1 === o2;
  }

  addBarcodeToCollectionIfMissing<Type extends Pick<IBarcode, 'id'>>(
    barcodeCollection: Type[],
    ...barcodesToCheck: (Type | null | undefined)[]
  ): Type[] {
    const barcodes: Type[] = barcodesToCheck.filter(isPresent);
    if (barcodes.length > 0) {
      const barcodeCollectionIdentifiers = barcodeCollection.map(barcodeItem => this.getBarcodeIdentifier(barcodeItem));
      const barcodesToAdd = barcodes.filter(barcodeItem => {
        const barcodeIdentifier = this.getBarcodeIdentifier(barcodeItem);
        if (barcodeCollectionIdentifiers.includes(barcodeIdentifier)) {
          return false;
        }
        barcodeCollectionIdentifiers.push(barcodeIdentifier);
        return true;
      });
      return [...barcodesToAdd, ...barcodeCollection];
    }
    return barcodeCollection;
  }
}
