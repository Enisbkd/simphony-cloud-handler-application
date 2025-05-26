import { Injectable, inject } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { isPresent } from 'app/core/util/operators';
import { ApplicationConfigService } from 'app/core/config/application-config.service';
import { createRequestOption } from 'app/core/request/request-util';
import { ITaxe, NewTaxe } from '../taxe.model';

export type PartialUpdateTaxe = Partial<ITaxe> & Pick<ITaxe, 'id'>;

export type EntityResponseType = HttpResponse<ITaxe>;
export type EntityArrayResponseType = HttpResponse<ITaxe[]>;

@Injectable({ providedIn: 'root' })
export class TaxeService {
  protected readonly http = inject(HttpClient);
  protected readonly applicationConfigService = inject(ApplicationConfigService);

  protected resourceUrl = this.applicationConfigService.getEndpointFor('api/taxes');

  create(taxe: NewTaxe): Observable<EntityResponseType> {
    return this.http.post<ITaxe>(this.resourceUrl, taxe, { observe: 'response' });
  }

  update(taxe: ITaxe): Observable<EntityResponseType> {
    return this.http.put<ITaxe>(`${this.resourceUrl}/${this.getTaxeIdentifier(taxe)}`, taxe, { observe: 'response' });
  }

  partialUpdate(taxe: PartialUpdateTaxe): Observable<EntityResponseType> {
    return this.http.patch<ITaxe>(`${this.resourceUrl}/${this.getTaxeIdentifier(taxe)}`, taxe, { observe: 'response' });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<ITaxe>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<ITaxe[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  getTaxeIdentifier(taxe: Pick<ITaxe, 'id'>): number {
    return taxe.id;
  }

  compareTaxe(o1: Pick<ITaxe, 'id'> | null, o2: Pick<ITaxe, 'id'> | null): boolean {
    return o1 && o2 ? this.getTaxeIdentifier(o1) === this.getTaxeIdentifier(o2) : o1 === o2;
  }

  addTaxeToCollectionIfMissing<Type extends Pick<ITaxe, 'id'>>(
    taxeCollection: Type[],
    ...taxesToCheck: (Type | null | undefined)[]
  ): Type[] {
    const taxes: Type[] = taxesToCheck.filter(isPresent);
    if (taxes.length > 0) {
      const taxeCollectionIdentifiers = taxeCollection.map(taxeItem => this.getTaxeIdentifier(taxeItem));
      const taxesToAdd = taxes.filter(taxeItem => {
        const taxeIdentifier = this.getTaxeIdentifier(taxeItem);
        if (taxeCollectionIdentifiers.includes(taxeIdentifier)) {
          return false;
        }
        taxeCollectionIdentifiers.push(taxeIdentifier);
        return true;
      });
      return [...taxesToAdd, ...taxeCollection];
    }
    return taxeCollection;
  }
}
