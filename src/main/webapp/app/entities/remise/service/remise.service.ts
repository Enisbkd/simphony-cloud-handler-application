import { Injectable, inject } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { isPresent } from 'app/core/util/operators';
import { ApplicationConfigService } from 'app/core/config/application-config.service';
import { createRequestOption } from 'app/core/request/request-util';
import { IRemise, NewRemise } from '../remise.model';

export type PartialUpdateRemise = Partial<IRemise> & Pick<IRemise, 'id'>;

export type EntityResponseType = HttpResponse<IRemise>;
export type EntityArrayResponseType = HttpResponse<IRemise[]>;

@Injectable({ providedIn: 'root' })
export class RemiseService {
  protected readonly http = inject(HttpClient);
  protected readonly applicationConfigService = inject(ApplicationConfigService);

  protected resourceUrl = this.applicationConfigService.getEndpointFor('api/remises');

  create(remise: NewRemise): Observable<EntityResponseType> {
    return this.http.post<IRemise>(this.resourceUrl, remise, { observe: 'response' });
  }

  update(remise: IRemise): Observable<EntityResponseType> {
    return this.http.put<IRemise>(`${this.resourceUrl}/${this.getRemiseIdentifier(remise)}`, remise, { observe: 'response' });
  }

  partialUpdate(remise: PartialUpdateRemise): Observable<EntityResponseType> {
    return this.http.patch<IRemise>(`${this.resourceUrl}/${this.getRemiseIdentifier(remise)}`, remise, { observe: 'response' });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<IRemise>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IRemise[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  getRemiseIdentifier(remise: Pick<IRemise, 'id'>): number {
    return remise.id;
  }

  compareRemise(o1: Pick<IRemise, 'id'> | null, o2: Pick<IRemise, 'id'> | null): boolean {
    return o1 && o2 ? this.getRemiseIdentifier(o1) === this.getRemiseIdentifier(o2) : o1 === o2;
  }

  addRemiseToCollectionIfMissing<Type extends Pick<IRemise, 'id'>>(
    remiseCollection: Type[],
    ...remisesToCheck: (Type | null | undefined)[]
  ): Type[] {
    const remises: Type[] = remisesToCheck.filter(isPresent);
    if (remises.length > 0) {
      const remiseCollectionIdentifiers = remiseCollection.map(remiseItem => this.getRemiseIdentifier(remiseItem));
      const remisesToAdd = remises.filter(remiseItem => {
        const remiseIdentifier = this.getRemiseIdentifier(remiseItem);
        if (remiseCollectionIdentifiers.includes(remiseIdentifier)) {
          return false;
        }
        remiseCollectionIdentifiers.push(remiseIdentifier);
        return true;
      });
      return [...remisesToAdd, ...remiseCollection];
    }
    return remiseCollection;
  }
}
