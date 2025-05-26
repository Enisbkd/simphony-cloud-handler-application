import { Injectable, inject } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { isPresent } from 'app/core/util/operators';
import { ApplicationConfigService } from 'app/core/config/application-config.service';
import { createRequestOption } from 'app/core/request/request-util';
import { IPartieDeJournee, NewPartieDeJournee } from '../partie-de-journee.model';

export type PartialUpdatePartieDeJournee = Partial<IPartieDeJournee> & Pick<IPartieDeJournee, 'id'>;

export type EntityResponseType = HttpResponse<IPartieDeJournee>;
export type EntityArrayResponseType = HttpResponse<IPartieDeJournee[]>;

@Injectable({ providedIn: 'root' })
export class PartieDeJourneeService {
  protected readonly http = inject(HttpClient);
  protected readonly applicationConfigService = inject(ApplicationConfigService);

  protected resourceUrl = this.applicationConfigService.getEndpointFor('api/partie-de-journees');

  create(partieDeJournee: NewPartieDeJournee): Observable<EntityResponseType> {
    return this.http.post<IPartieDeJournee>(this.resourceUrl, partieDeJournee, { observe: 'response' });
  }

  update(partieDeJournee: IPartieDeJournee): Observable<EntityResponseType> {
    return this.http.put<IPartieDeJournee>(`${this.resourceUrl}/${this.getPartieDeJourneeIdentifier(partieDeJournee)}`, partieDeJournee, {
      observe: 'response',
    });
  }

  partialUpdate(partieDeJournee: PartialUpdatePartieDeJournee): Observable<EntityResponseType> {
    return this.http.patch<IPartieDeJournee>(`${this.resourceUrl}/${this.getPartieDeJourneeIdentifier(partieDeJournee)}`, partieDeJournee, {
      observe: 'response',
    });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<IPartieDeJournee>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IPartieDeJournee[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  getPartieDeJourneeIdentifier(partieDeJournee: Pick<IPartieDeJournee, 'id'>): number {
    return partieDeJournee.id;
  }

  comparePartieDeJournee(o1: Pick<IPartieDeJournee, 'id'> | null, o2: Pick<IPartieDeJournee, 'id'> | null): boolean {
    return o1 && o2 ? this.getPartieDeJourneeIdentifier(o1) === this.getPartieDeJourneeIdentifier(o2) : o1 === o2;
  }

  addPartieDeJourneeToCollectionIfMissing<Type extends Pick<IPartieDeJournee, 'id'>>(
    partieDeJourneeCollection: Type[],
    ...partieDeJourneesToCheck: (Type | null | undefined)[]
  ): Type[] {
    const partieDeJournees: Type[] = partieDeJourneesToCheck.filter(isPresent);
    if (partieDeJournees.length > 0) {
      const partieDeJourneeCollectionIdentifiers = partieDeJourneeCollection.map(partieDeJourneeItem =>
        this.getPartieDeJourneeIdentifier(partieDeJourneeItem),
      );
      const partieDeJourneesToAdd = partieDeJournees.filter(partieDeJourneeItem => {
        const partieDeJourneeIdentifier = this.getPartieDeJourneeIdentifier(partieDeJourneeItem);
        if (partieDeJourneeCollectionIdentifiers.includes(partieDeJourneeIdentifier)) {
          return false;
        }
        partieDeJourneeCollectionIdentifiers.push(partieDeJourneeIdentifier);
        return true;
      });
      return [...partieDeJourneesToAdd, ...partieDeJourneeCollection];
    }
    return partieDeJourneeCollection;
  }
}
