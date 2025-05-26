import { Injectable, inject } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { isPresent } from 'app/core/util/operators';
import { ApplicationConfigService } from 'app/core/config/application-config.service';
import { createRequestOption } from 'app/core/request/request-util';
import { IElementMenu, NewElementMenu } from '../element-menu.model';

export type PartialUpdateElementMenu = Partial<IElementMenu> & Pick<IElementMenu, 'id'>;

export type EntityResponseType = HttpResponse<IElementMenu>;
export type EntityArrayResponseType = HttpResponse<IElementMenu[]>;

@Injectable({ providedIn: 'root' })
export class ElementMenuService {
  protected readonly http = inject(HttpClient);
  protected readonly applicationConfigService = inject(ApplicationConfigService);

  protected resourceUrl = this.applicationConfigService.getEndpointFor('api/element-menus');

  create(elementMenu: NewElementMenu): Observable<EntityResponseType> {
    return this.http.post<IElementMenu>(this.resourceUrl, elementMenu, { observe: 'response' });
  }

  update(elementMenu: IElementMenu): Observable<EntityResponseType> {
    return this.http.put<IElementMenu>(`${this.resourceUrl}/${this.getElementMenuIdentifier(elementMenu)}`, elementMenu, {
      observe: 'response',
    });
  }

  partialUpdate(elementMenu: PartialUpdateElementMenu): Observable<EntityResponseType> {
    return this.http.patch<IElementMenu>(`${this.resourceUrl}/${this.getElementMenuIdentifier(elementMenu)}`, elementMenu, {
      observe: 'response',
    });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<IElementMenu>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IElementMenu[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  getElementMenuIdentifier(elementMenu: Pick<IElementMenu, 'id'>): number {
    return elementMenu.id;
  }

  compareElementMenu(o1: Pick<IElementMenu, 'id'> | null, o2: Pick<IElementMenu, 'id'> | null): boolean {
    return o1 && o2 ? this.getElementMenuIdentifier(o1) === this.getElementMenuIdentifier(o2) : o1 === o2;
  }

  addElementMenuToCollectionIfMissing<Type extends Pick<IElementMenu, 'id'>>(
    elementMenuCollection: Type[],
    ...elementMenusToCheck: (Type | null | undefined)[]
  ): Type[] {
    const elementMenus: Type[] = elementMenusToCheck.filter(isPresent);
    if (elementMenus.length > 0) {
      const elementMenuCollectionIdentifiers = elementMenuCollection.map(elementMenuItem => this.getElementMenuIdentifier(elementMenuItem));
      const elementMenusToAdd = elementMenus.filter(elementMenuItem => {
        const elementMenuIdentifier = this.getElementMenuIdentifier(elementMenuItem);
        if (elementMenuCollectionIdentifiers.includes(elementMenuIdentifier)) {
          return false;
        }
        elementMenuCollectionIdentifiers.push(elementMenuIdentifier);
        return true;
      });
      return [...elementMenusToAdd, ...elementMenuCollection];
    }
    return elementMenuCollection;
  }
}
