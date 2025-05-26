import { Injectable, inject } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { isPresent } from 'app/core/util/operators';
import { ApplicationConfigService } from 'app/core/config/application-config.service';
import { createRequestOption } from 'app/core/request/request-util';
import { IHier, NewHier } from '../hier.model';

export type PartialUpdateHier = Partial<IHier> & Pick<IHier, 'id'>;

export type EntityResponseType = HttpResponse<IHier>;
export type EntityArrayResponseType = HttpResponse<IHier[]>;

@Injectable({ providedIn: 'root' })
export class HierService {
  protected readonly http = inject(HttpClient);
  protected readonly applicationConfigService = inject(ApplicationConfigService);

  protected resourceUrl = this.applicationConfigService.getEndpointFor('api/hiers');

  create(hier: NewHier): Observable<EntityResponseType> {
    return this.http.post<IHier>(this.resourceUrl, hier, { observe: 'response' });
  }

  update(hier: IHier): Observable<EntityResponseType> {
    return this.http.put<IHier>(`${this.resourceUrl}/${this.getHierIdentifier(hier)}`, hier, { observe: 'response' });
  }

  partialUpdate(hier: PartialUpdateHier): Observable<EntityResponseType> {
    return this.http.patch<IHier>(`${this.resourceUrl}/${this.getHierIdentifier(hier)}`, hier, { observe: 'response' });
  }

  find(id: string): Observable<EntityResponseType> {
    return this.http.get<IHier>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IHier[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: string): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  getHierIdentifier(hier: Pick<IHier, 'id'>): string {
    return hier.id;
  }

  compareHier(o1: Pick<IHier, 'id'> | null, o2: Pick<IHier, 'id'> | null): boolean {
    return o1 && o2 ? this.getHierIdentifier(o1) === this.getHierIdentifier(o2) : o1 === o2;
  }

  addHierToCollectionIfMissing<Type extends Pick<IHier, 'id'>>(
    hierCollection: Type[],
    ...hiersToCheck: (Type | null | undefined)[]
  ): Type[] {
    const hiers: Type[] = hiersToCheck.filter(isPresent);
    if (hiers.length > 0) {
      const hierCollectionIdentifiers = hierCollection.map(hierItem => this.getHierIdentifier(hierItem));
      const hiersToAdd = hiers.filter(hierItem => {
        const hierIdentifier = this.getHierIdentifier(hierItem);
        if (hierCollectionIdentifiers.includes(hierIdentifier)) {
          return false;
        }
        hierCollectionIdentifiers.push(hierIdentifier);
        return true;
      });
      return [...hiersToAdd, ...hierCollection];
    }
    return hierCollection;
  }
}
