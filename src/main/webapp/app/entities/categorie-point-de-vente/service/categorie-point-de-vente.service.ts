import { Injectable, inject } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { isPresent } from 'app/core/util/operators';
import { ApplicationConfigService } from 'app/core/config/application-config.service';
import { createRequestOption } from 'app/core/request/request-util';
import { ICategoriePointDeVente, NewCategoriePointDeVente } from '../categorie-point-de-vente.model';

export type PartialUpdateCategoriePointDeVente = Partial<ICategoriePointDeVente> & Pick<ICategoriePointDeVente, 'id'>;

export type EntityResponseType = HttpResponse<ICategoriePointDeVente>;
export type EntityArrayResponseType = HttpResponse<ICategoriePointDeVente[]>;

@Injectable({ providedIn: 'root' })
export class CategoriePointDeVenteService {
  protected readonly http = inject(HttpClient);
  protected readonly applicationConfigService = inject(ApplicationConfigService);

  protected resourceUrl = this.applicationConfigService.getEndpointFor('api/categorie-point-de-ventes');

  create(categoriePointDeVente: NewCategoriePointDeVente): Observable<EntityResponseType> {
    return this.http.post<ICategoriePointDeVente>(this.resourceUrl, categoriePointDeVente, { observe: 'response' });
  }

  update(categoriePointDeVente: ICategoriePointDeVente): Observable<EntityResponseType> {
    return this.http.put<ICategoriePointDeVente>(
      `${this.resourceUrl}/${this.getCategoriePointDeVenteIdentifier(categoriePointDeVente)}`,
      categoriePointDeVente,
      { observe: 'response' },
    );
  }

  partialUpdate(categoriePointDeVente: PartialUpdateCategoriePointDeVente): Observable<EntityResponseType> {
    return this.http.patch<ICategoriePointDeVente>(
      `${this.resourceUrl}/${this.getCategoriePointDeVenteIdentifier(categoriePointDeVente)}`,
      categoriePointDeVente,
      { observe: 'response' },
    );
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<ICategoriePointDeVente>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<ICategoriePointDeVente[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  getCategoriePointDeVenteIdentifier(categoriePointDeVente: Pick<ICategoriePointDeVente, 'id'>): number {
    return categoriePointDeVente.id;
  }

  compareCategoriePointDeVente(o1: Pick<ICategoriePointDeVente, 'id'> | null, o2: Pick<ICategoriePointDeVente, 'id'> | null): boolean {
    return o1 && o2 ? this.getCategoriePointDeVenteIdentifier(o1) === this.getCategoriePointDeVenteIdentifier(o2) : o1 === o2;
  }

  addCategoriePointDeVenteToCollectionIfMissing<Type extends Pick<ICategoriePointDeVente, 'id'>>(
    categoriePointDeVenteCollection: Type[],
    ...categoriePointDeVentesToCheck: (Type | null | undefined)[]
  ): Type[] {
    const categoriePointDeVentes: Type[] = categoriePointDeVentesToCheck.filter(isPresent);
    if (categoriePointDeVentes.length > 0) {
      const categoriePointDeVenteCollectionIdentifiers = categoriePointDeVenteCollection.map(categoriePointDeVenteItem =>
        this.getCategoriePointDeVenteIdentifier(categoriePointDeVenteItem),
      );
      const categoriePointDeVentesToAdd = categoriePointDeVentes.filter(categoriePointDeVenteItem => {
        const categoriePointDeVenteIdentifier = this.getCategoriePointDeVenteIdentifier(categoriePointDeVenteItem);
        if (categoriePointDeVenteCollectionIdentifiers.includes(categoriePointDeVenteIdentifier)) {
          return false;
        }
        categoriePointDeVenteCollectionIdentifiers.push(categoriePointDeVenteIdentifier);
        return true;
      });
      return [...categoriePointDeVentesToAdd, ...categoriePointDeVenteCollection];
    }
    return categoriePointDeVenteCollection;
  }
}
