import { Injectable, inject } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable, map } from 'rxjs';

import dayjs from 'dayjs/esm';

import { isPresent } from 'app/core/util/operators';
import { ApplicationConfigService } from 'app/core/config/application-config.service';
import { createRequestOption } from 'app/core/request/request-util';
import { IFactureDetail, NewFactureDetail } from '../facture-detail.model';

export type PartialUpdateFactureDetail = Partial<IFactureDetail> & Pick<IFactureDetail, 'id'>;

type RestOf<T extends IFactureDetail | NewFactureDetail> = Omit<T, 'utcDateTime' | 'lclDateTime'> & {
  utcDateTime?: string | null;
  lclDateTime?: string | null;
};

export type RestFactureDetail = RestOf<IFactureDetail>;

export type NewRestFactureDetail = RestOf<NewFactureDetail>;

export type PartialUpdateRestFactureDetail = RestOf<PartialUpdateFactureDetail>;

export type EntityResponseType = HttpResponse<IFactureDetail>;
export type EntityArrayResponseType = HttpResponse<IFactureDetail[]>;

@Injectable({ providedIn: 'root' })
export class FactureDetailService {
  protected readonly http = inject(HttpClient);
  protected readonly applicationConfigService = inject(ApplicationConfigService);

  protected resourceUrl = this.applicationConfigService.getEndpointFor('api/facture-details');

  create(factureDetail: NewFactureDetail): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(factureDetail);
    return this.http
      .post<RestFactureDetail>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map(res => this.convertResponseFromServer(res)));
  }

  update(factureDetail: IFactureDetail): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(factureDetail);
    return this.http
      .put<RestFactureDetail>(`${this.resourceUrl}/${this.getFactureDetailIdentifier(factureDetail)}`, copy, { observe: 'response' })
      .pipe(map(res => this.convertResponseFromServer(res)));
  }

  partialUpdate(factureDetail: PartialUpdateFactureDetail): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(factureDetail);
    return this.http
      .patch<RestFactureDetail>(`${this.resourceUrl}/${this.getFactureDetailIdentifier(factureDetail)}`, copy, { observe: 'response' })
      .pipe(map(res => this.convertResponseFromServer(res)));
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http
      .get<RestFactureDetail>(`${this.resourceUrl}/${id}`, { observe: 'response' })
      .pipe(map(res => this.convertResponseFromServer(res)));
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<RestFactureDetail[]>(this.resourceUrl, { params: options, observe: 'response' })
      .pipe(map(res => this.convertResponseArrayFromServer(res)));
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  getFactureDetailIdentifier(factureDetail: Pick<IFactureDetail, 'id'>): number {
    return factureDetail.id;
  }

  compareFactureDetail(o1: Pick<IFactureDetail, 'id'> | null, o2: Pick<IFactureDetail, 'id'> | null): boolean {
    return o1 && o2 ? this.getFactureDetailIdentifier(o1) === this.getFactureDetailIdentifier(o2) : o1 === o2;
  }

  addFactureDetailToCollectionIfMissing<Type extends Pick<IFactureDetail, 'id'>>(
    factureDetailCollection: Type[],
    ...factureDetailsToCheck: (Type | null | undefined)[]
  ): Type[] {
    const factureDetails: Type[] = factureDetailsToCheck.filter(isPresent);
    if (factureDetails.length > 0) {
      const factureDetailCollectionIdentifiers = factureDetailCollection.map(factureDetailItem =>
        this.getFactureDetailIdentifier(factureDetailItem),
      );
      const factureDetailsToAdd = factureDetails.filter(factureDetailItem => {
        const factureDetailIdentifier = this.getFactureDetailIdentifier(factureDetailItem);
        if (factureDetailCollectionIdentifiers.includes(factureDetailIdentifier)) {
          return false;
        }
        factureDetailCollectionIdentifiers.push(factureDetailIdentifier);
        return true;
      });
      return [...factureDetailsToAdd, ...factureDetailCollection];
    }
    return factureDetailCollection;
  }

  protected convertDateFromClient<T extends IFactureDetail | NewFactureDetail | PartialUpdateFactureDetail>(factureDetail: T): RestOf<T> {
    return {
      ...factureDetail,
      utcDateTime: factureDetail.utcDateTime?.toJSON() ?? null,
      lclDateTime: factureDetail.lclDateTime?.toJSON() ?? null,
    };
  }

  protected convertDateFromServer(restFactureDetail: RestFactureDetail): IFactureDetail {
    return {
      ...restFactureDetail,
      utcDateTime: restFactureDetail.utcDateTime ? dayjs(restFactureDetail.utcDateTime) : undefined,
      lclDateTime: restFactureDetail.lclDateTime ? dayjs(restFactureDetail.lclDateTime) : undefined,
    };
  }

  protected convertResponseFromServer(res: HttpResponse<RestFactureDetail>): HttpResponse<IFactureDetail> {
    return res.clone({
      body: res.body ? this.convertDateFromServer(res.body) : null,
    });
  }

  protected convertResponseArrayFromServer(res: HttpResponse<RestFactureDetail[]>): HttpResponse<IFactureDetail[]> {
    return res.clone({
      body: res.body ? res.body.map(item => this.convertDateFromServer(item)) : null,
    });
  }
}
