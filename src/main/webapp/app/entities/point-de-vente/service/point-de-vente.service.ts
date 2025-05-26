import { Injectable, inject } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { isPresent } from 'app/core/util/operators';
import { ApplicationConfigService } from 'app/core/config/application-config.service';
import { createRequestOption } from 'app/core/request/request-util';
import { IPointDeVente, NewPointDeVente } from '../point-de-vente.model';

export type PartialUpdatePointDeVente = Partial<IPointDeVente> & Pick<IPointDeVente, 'id'>;

export type EntityResponseType = HttpResponse<IPointDeVente>;
export type EntityArrayResponseType = HttpResponse<IPointDeVente[]>;

@Injectable({ providedIn: 'root' })
export class PointDeVenteService {
  protected readonly http = inject(HttpClient);
  protected readonly applicationConfigService = inject(ApplicationConfigService);

  protected resourceUrl = this.applicationConfigService.getEndpointFor('api/point-de-ventes');

  create(pointDeVente: NewPointDeVente): Observable<EntityResponseType> {
    return this.http.post<IPointDeVente>(this.resourceUrl, pointDeVente, { observe: 'response' });
  }

  update(pointDeVente: IPointDeVente): Observable<EntityResponseType> {
    return this.http.put<IPointDeVente>(`${this.resourceUrl}/${this.getPointDeVenteIdentifier(pointDeVente)}`, pointDeVente, {
      observe: 'response',
    });
  }

  partialUpdate(pointDeVente: PartialUpdatePointDeVente): Observable<EntityResponseType> {
    return this.http.patch<IPointDeVente>(`${this.resourceUrl}/${this.getPointDeVenteIdentifier(pointDeVente)}`, pointDeVente, {
      observe: 'response',
    });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<IPointDeVente>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IPointDeVente[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  getPointDeVenteIdentifier(pointDeVente: Pick<IPointDeVente, 'id'>): number {
    return pointDeVente.id;
  }

  comparePointDeVente(o1: Pick<IPointDeVente, 'id'> | null, o2: Pick<IPointDeVente, 'id'> | null): boolean {
    return o1 && o2 ? this.getPointDeVenteIdentifier(o1) === this.getPointDeVenteIdentifier(o2) : o1 === o2;
  }

  addPointDeVenteToCollectionIfMissing<Type extends Pick<IPointDeVente, 'id'>>(
    pointDeVenteCollection: Type[],
    ...pointDeVentesToCheck: (Type | null | undefined)[]
  ): Type[] {
    const pointDeVentes: Type[] = pointDeVentesToCheck.filter(isPresent);
    if (pointDeVentes.length > 0) {
      const pointDeVenteCollectionIdentifiers = pointDeVenteCollection.map(pointDeVenteItem =>
        this.getPointDeVenteIdentifier(pointDeVenteItem),
      );
      const pointDeVentesToAdd = pointDeVentes.filter(pointDeVenteItem => {
        const pointDeVenteIdentifier = this.getPointDeVenteIdentifier(pointDeVenteItem);
        if (pointDeVenteCollectionIdentifiers.includes(pointDeVenteIdentifier)) {
          return false;
        }
        pointDeVenteCollectionIdentifiers.push(pointDeVenteIdentifier);
        return true;
      });
      return [...pointDeVentesToAdd, ...pointDeVenteCollection];
    }
    return pointDeVenteCollection;
  }
}
