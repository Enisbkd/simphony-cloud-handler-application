import { Injectable, inject } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable, map } from 'rxjs';

import dayjs from 'dayjs/esm';

import { isPresent } from 'app/core/util/operators';
import { ApplicationConfigService } from 'app/core/config/application-config.service';
import { createRequestOption } from 'app/core/request/request-util';
import { IFactureEnTete, NewFactureEnTete } from '../facture-en-tete.model';

export type PartialUpdateFactureEnTete = Partial<IFactureEnTete> & Pick<IFactureEnTete, 'id'>;

type RestOf<T extends IFactureEnTete | NewFactureEnTete> = Omit<T, 'ouvertureDateTime' | 'fermetureDateTime'> & {
  ouvertureDateTime?: string | null;
  fermetureDateTime?: string | null;
};

export type RestFactureEnTete = RestOf<IFactureEnTete>;

export type NewRestFactureEnTete = RestOf<NewFactureEnTete>;

export type PartialUpdateRestFactureEnTete = RestOf<PartialUpdateFactureEnTete>;

export type EntityResponseType = HttpResponse<IFactureEnTete>;
export type EntityArrayResponseType = HttpResponse<IFactureEnTete[]>;

@Injectable({ providedIn: 'root' })
export class FactureEnTeteService {
  protected readonly http = inject(HttpClient);
  protected readonly applicationConfigService = inject(ApplicationConfigService);

  protected resourceUrl = this.applicationConfigService.getEndpointFor('api/facture-en-tetes');

  create(factureEnTete: NewFactureEnTete): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(factureEnTete);
    return this.http
      .post<RestFactureEnTete>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map(res => this.convertResponseFromServer(res)));
  }

  update(factureEnTete: IFactureEnTete): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(factureEnTete);
    return this.http
      .put<RestFactureEnTete>(`${this.resourceUrl}/${this.getFactureEnTeteIdentifier(factureEnTete)}`, copy, { observe: 'response' })
      .pipe(map(res => this.convertResponseFromServer(res)));
  }

  partialUpdate(factureEnTete: PartialUpdateFactureEnTete): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(factureEnTete);
    return this.http
      .patch<RestFactureEnTete>(`${this.resourceUrl}/${this.getFactureEnTeteIdentifier(factureEnTete)}`, copy, { observe: 'response' })
      .pipe(map(res => this.convertResponseFromServer(res)));
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http
      .get<RestFactureEnTete>(`${this.resourceUrl}/${id}`, { observe: 'response' })
      .pipe(map(res => this.convertResponseFromServer(res)));
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<RestFactureEnTete[]>(this.resourceUrl, { params: options, observe: 'response' })
      .pipe(map(res => this.convertResponseArrayFromServer(res)));
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  getFactureEnTeteIdentifier(factureEnTete: Pick<IFactureEnTete, 'id'>): number {
    return factureEnTete.id;
  }

  compareFactureEnTete(o1: Pick<IFactureEnTete, 'id'> | null, o2: Pick<IFactureEnTete, 'id'> | null): boolean {
    return o1 && o2 ? this.getFactureEnTeteIdentifier(o1) === this.getFactureEnTeteIdentifier(o2) : o1 === o2;
  }

  addFactureEnTeteToCollectionIfMissing<Type extends Pick<IFactureEnTete, 'id'>>(
    factureEnTeteCollection: Type[],
    ...factureEnTetesToCheck: (Type | null | undefined)[]
  ): Type[] {
    const factureEnTetes: Type[] = factureEnTetesToCheck.filter(isPresent);
    if (factureEnTetes.length > 0) {
      const factureEnTeteCollectionIdentifiers = factureEnTeteCollection.map(factureEnTeteItem =>
        this.getFactureEnTeteIdentifier(factureEnTeteItem),
      );
      const factureEnTetesToAdd = factureEnTetes.filter(factureEnTeteItem => {
        const factureEnTeteIdentifier = this.getFactureEnTeteIdentifier(factureEnTeteItem);
        if (factureEnTeteCollectionIdentifiers.includes(factureEnTeteIdentifier)) {
          return false;
        }
        factureEnTeteCollectionIdentifiers.push(factureEnTeteIdentifier);
        return true;
      });
      return [...factureEnTetesToAdd, ...factureEnTeteCollection];
    }
    return factureEnTeteCollection;
  }

  protected convertDateFromClient<T extends IFactureEnTete | NewFactureEnTete | PartialUpdateFactureEnTete>(factureEnTete: T): RestOf<T> {
    return {
      ...factureEnTete,
      ouvertureDateTime: factureEnTete.ouvertureDateTime?.toJSON() ?? null,
      fermetureDateTime: factureEnTete.fermetureDateTime?.toJSON() ?? null,
    };
  }

  protected convertDateFromServer(restFactureEnTete: RestFactureEnTete): IFactureEnTete {
    return {
      ...restFactureEnTete,
      ouvertureDateTime: restFactureEnTete.ouvertureDateTime ? dayjs(restFactureEnTete.ouvertureDateTime) : undefined,
      fermetureDateTime: restFactureEnTete.fermetureDateTime ? dayjs(restFactureEnTete.fermetureDateTime) : undefined,
    };
  }

  protected convertResponseFromServer(res: HttpResponse<RestFactureEnTete>): HttpResponse<IFactureEnTete> {
    return res.clone({
      body: res.body ? this.convertDateFromServer(res.body) : null,
    });
  }

  protected convertResponseArrayFromServer(res: HttpResponse<RestFactureEnTete[]>): HttpResponse<IFactureEnTete[]> {
    return res.clone({
      body: res.body ? res.body.map(item => this.convertDateFromServer(item)) : null,
    });
  }
}
