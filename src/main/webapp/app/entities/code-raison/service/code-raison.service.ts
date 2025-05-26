import { Injectable, inject } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { isPresent } from 'app/core/util/operators';
import { ApplicationConfigService } from 'app/core/config/application-config.service';
import { createRequestOption } from 'app/core/request/request-util';
import { ICodeRaison, NewCodeRaison } from '../code-raison.model';

export type PartialUpdateCodeRaison = Partial<ICodeRaison> & Pick<ICodeRaison, 'id'>;

export type EntityResponseType = HttpResponse<ICodeRaison>;
export type EntityArrayResponseType = HttpResponse<ICodeRaison[]>;

@Injectable({ providedIn: 'root' })
export class CodeRaisonService {
  protected readonly http = inject(HttpClient);
  protected readonly applicationConfigService = inject(ApplicationConfigService);

  protected resourceUrl = this.applicationConfigService.getEndpointFor('api/code-raisons');

  create(codeRaison: NewCodeRaison): Observable<EntityResponseType> {
    return this.http.post<ICodeRaison>(this.resourceUrl, codeRaison, { observe: 'response' });
  }

  update(codeRaison: ICodeRaison): Observable<EntityResponseType> {
    return this.http.put<ICodeRaison>(`${this.resourceUrl}/${this.getCodeRaisonIdentifier(codeRaison)}`, codeRaison, {
      observe: 'response',
    });
  }

  partialUpdate(codeRaison: PartialUpdateCodeRaison): Observable<EntityResponseType> {
    return this.http.patch<ICodeRaison>(`${this.resourceUrl}/${this.getCodeRaisonIdentifier(codeRaison)}`, codeRaison, {
      observe: 'response',
    });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<ICodeRaison>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<ICodeRaison[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  getCodeRaisonIdentifier(codeRaison: Pick<ICodeRaison, 'id'>): number {
    return codeRaison.id;
  }

  compareCodeRaison(o1: Pick<ICodeRaison, 'id'> | null, o2: Pick<ICodeRaison, 'id'> | null): boolean {
    return o1 && o2 ? this.getCodeRaisonIdentifier(o1) === this.getCodeRaisonIdentifier(o2) : o1 === o2;
  }

  addCodeRaisonToCollectionIfMissing<Type extends Pick<ICodeRaison, 'id'>>(
    codeRaisonCollection: Type[],
    ...codeRaisonsToCheck: (Type | null | undefined)[]
  ): Type[] {
    const codeRaisons: Type[] = codeRaisonsToCheck.filter(isPresent);
    if (codeRaisons.length > 0) {
      const codeRaisonCollectionIdentifiers = codeRaisonCollection.map(codeRaisonItem => this.getCodeRaisonIdentifier(codeRaisonItem));
      const codeRaisonsToAdd = codeRaisons.filter(codeRaisonItem => {
        const codeRaisonIdentifier = this.getCodeRaisonIdentifier(codeRaisonItem);
        if (codeRaisonCollectionIdentifiers.includes(codeRaisonIdentifier)) {
          return false;
        }
        codeRaisonCollectionIdentifiers.push(codeRaisonIdentifier);
        return true;
      });
      return [...codeRaisonsToAdd, ...codeRaisonCollection];
    }
    return codeRaisonCollection;
  }
}
