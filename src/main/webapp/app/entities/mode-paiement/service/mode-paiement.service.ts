import { Injectable, inject } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { isPresent } from 'app/core/util/operators';
import { ApplicationConfigService } from 'app/core/config/application-config.service';
import { createRequestOption } from 'app/core/request/request-util';
import { IModePaiement, NewModePaiement } from '../mode-paiement.model';

export type PartialUpdateModePaiement = Partial<IModePaiement> & Pick<IModePaiement, 'id'>;

export type EntityResponseType = HttpResponse<IModePaiement>;
export type EntityArrayResponseType = HttpResponse<IModePaiement[]>;

@Injectable({ providedIn: 'root' })
export class ModePaiementService {
  protected readonly http = inject(HttpClient);
  protected readonly applicationConfigService = inject(ApplicationConfigService);

  protected resourceUrl = this.applicationConfigService.getEndpointFor('api/mode-paiements');

  create(modePaiement: NewModePaiement): Observable<EntityResponseType> {
    return this.http.post<IModePaiement>(this.resourceUrl, modePaiement, { observe: 'response' });
  }

  update(modePaiement: IModePaiement): Observable<EntityResponseType> {
    return this.http.put<IModePaiement>(`${this.resourceUrl}/${this.getModePaiementIdentifier(modePaiement)}`, modePaiement, {
      observe: 'response',
    });
  }

  partialUpdate(modePaiement: PartialUpdateModePaiement): Observable<EntityResponseType> {
    return this.http.patch<IModePaiement>(`${this.resourceUrl}/${this.getModePaiementIdentifier(modePaiement)}`, modePaiement, {
      observe: 'response',
    });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<IModePaiement>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IModePaiement[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  getModePaiementIdentifier(modePaiement: Pick<IModePaiement, 'id'>): number {
    return modePaiement.id;
  }

  compareModePaiement(o1: Pick<IModePaiement, 'id'> | null, o2: Pick<IModePaiement, 'id'> | null): boolean {
    return o1 && o2 ? this.getModePaiementIdentifier(o1) === this.getModePaiementIdentifier(o2) : o1 === o2;
  }

  addModePaiementToCollectionIfMissing<Type extends Pick<IModePaiement, 'id'>>(
    modePaiementCollection: Type[],
    ...modePaiementsToCheck: (Type | null | undefined)[]
  ): Type[] {
    const modePaiements: Type[] = modePaiementsToCheck.filter(isPresent);
    if (modePaiements.length > 0) {
      const modePaiementCollectionIdentifiers = modePaiementCollection.map(modePaiementItem =>
        this.getModePaiementIdentifier(modePaiementItem),
      );
      const modePaiementsToAdd = modePaiements.filter(modePaiementItem => {
        const modePaiementIdentifier = this.getModePaiementIdentifier(modePaiementItem);
        if (modePaiementCollectionIdentifiers.includes(modePaiementIdentifier)) {
          return false;
        }
        modePaiementCollectionIdentifiers.push(modePaiementIdentifier);
        return true;
      });
      return [...modePaiementsToAdd, ...modePaiementCollection];
    }
    return modePaiementCollection;
  }
}
