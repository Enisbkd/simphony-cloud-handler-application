import { Injectable, inject } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { isPresent } from 'app/core/util/operators';
import { ApplicationConfigService } from 'app/core/config/application-config.service';
import { createRequestOption } from 'app/core/request/request-util';
import { IFamilyGroup, NewFamilyGroup } from '../family-group.model';

export type PartialUpdateFamilyGroup = Partial<IFamilyGroup> & Pick<IFamilyGroup, 'id'>;

export type EntityResponseType = HttpResponse<IFamilyGroup>;
export type EntityArrayResponseType = HttpResponse<IFamilyGroup[]>;

@Injectable({ providedIn: 'root' })
export class FamilyGroupService {
  protected readonly http = inject(HttpClient);
  protected readonly applicationConfigService = inject(ApplicationConfigService);

  protected resourceUrl = this.applicationConfigService.getEndpointFor('api/family-groups');

  create(familyGroup: NewFamilyGroup): Observable<EntityResponseType> {
    return this.http.post<IFamilyGroup>(this.resourceUrl, familyGroup, { observe: 'response' });
  }

  update(familyGroup: IFamilyGroup): Observable<EntityResponseType> {
    return this.http.put<IFamilyGroup>(`${this.resourceUrl}/${this.getFamilyGroupIdentifier(familyGroup)}`, familyGroup, {
      observe: 'response',
    });
  }

  partialUpdate(familyGroup: PartialUpdateFamilyGroup): Observable<EntityResponseType> {
    return this.http.patch<IFamilyGroup>(`${this.resourceUrl}/${this.getFamilyGroupIdentifier(familyGroup)}`, familyGroup, {
      observe: 'response',
    });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<IFamilyGroup>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IFamilyGroup[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  getFamilyGroupIdentifier(familyGroup: Pick<IFamilyGroup, 'id'>): number {
    return familyGroup.id;
  }

  compareFamilyGroup(o1: Pick<IFamilyGroup, 'id'> | null, o2: Pick<IFamilyGroup, 'id'> | null): boolean {
    return o1 && o2 ? this.getFamilyGroupIdentifier(o1) === this.getFamilyGroupIdentifier(o2) : o1 === o2;
  }

  addFamilyGroupToCollectionIfMissing<Type extends Pick<IFamilyGroup, 'id'>>(
    familyGroupCollection: Type[],
    ...familyGroupsToCheck: (Type | null | undefined)[]
  ): Type[] {
    const familyGroups: Type[] = familyGroupsToCheck.filter(isPresent);
    if (familyGroups.length > 0) {
      const familyGroupCollectionIdentifiers = familyGroupCollection.map(familyGroupItem => this.getFamilyGroupIdentifier(familyGroupItem));
      const familyGroupsToAdd = familyGroups.filter(familyGroupItem => {
        const familyGroupIdentifier = this.getFamilyGroupIdentifier(familyGroupItem);
        if (familyGroupCollectionIdentifiers.includes(familyGroupIdentifier)) {
          return false;
        }
        familyGroupCollectionIdentifiers.push(familyGroupIdentifier);
        return true;
      });
      return [...familyGroupsToAdd, ...familyGroupCollection];
    }
    return familyGroupCollection;
  }
}
