import { Injectable, inject } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { isPresent } from 'app/core/util/operators';
import { ApplicationConfigService } from 'app/core/config/application-config.service';
import { createRequestOption } from 'app/core/request/request-util';
import { IMajorGroup, NewMajorGroup } from '../major-group.model';

export type PartialUpdateMajorGroup = Partial<IMajorGroup> & Pick<IMajorGroup, 'id'>;

export type EntityResponseType = HttpResponse<IMajorGroup>;
export type EntityArrayResponseType = HttpResponse<IMajorGroup[]>;

@Injectable({ providedIn: 'root' })
export class MajorGroupService {
  protected readonly http = inject(HttpClient);
  protected readonly applicationConfigService = inject(ApplicationConfigService);

  protected resourceUrl = this.applicationConfigService.getEndpointFor('api/major-groups');

  create(majorGroup: NewMajorGroup): Observable<EntityResponseType> {
    return this.http.post<IMajorGroup>(this.resourceUrl, majorGroup, { observe: 'response' });
  }

  update(majorGroup: IMajorGroup): Observable<EntityResponseType> {
    return this.http.put<IMajorGroup>(`${this.resourceUrl}/${this.getMajorGroupIdentifier(majorGroup)}`, majorGroup, {
      observe: 'response',
    });
  }

  partialUpdate(majorGroup: PartialUpdateMajorGroup): Observable<EntityResponseType> {
    return this.http.patch<IMajorGroup>(`${this.resourceUrl}/${this.getMajorGroupIdentifier(majorGroup)}`, majorGroup, {
      observe: 'response',
    });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<IMajorGroup>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IMajorGroup[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  getMajorGroupIdentifier(majorGroup: Pick<IMajorGroup, 'id'>): number {
    return majorGroup.id;
  }

  compareMajorGroup(o1: Pick<IMajorGroup, 'id'> | null, o2: Pick<IMajorGroup, 'id'> | null): boolean {
    return o1 && o2 ? this.getMajorGroupIdentifier(o1) === this.getMajorGroupIdentifier(o2) : o1 === o2;
  }

  addMajorGroupToCollectionIfMissing<Type extends Pick<IMajorGroup, 'id'>>(
    majorGroupCollection: Type[],
    ...majorGroupsToCheck: (Type | null | undefined)[]
  ): Type[] {
    const majorGroups: Type[] = majorGroupsToCheck.filter(isPresent);
    if (majorGroups.length > 0) {
      const majorGroupCollectionIdentifiers = majorGroupCollection.map(majorGroupItem => this.getMajorGroupIdentifier(majorGroupItem));
      const majorGroupsToAdd = majorGroups.filter(majorGroupItem => {
        const majorGroupIdentifier = this.getMajorGroupIdentifier(majorGroupItem);
        if (majorGroupCollectionIdentifiers.includes(majorGroupIdentifier)) {
          return false;
        }
        majorGroupCollectionIdentifiers.push(majorGroupIdentifier);
        return true;
      });
      return [...majorGroupsToAdd, ...majorGroupCollection];
    }
    return majorGroupCollection;
  }
}
