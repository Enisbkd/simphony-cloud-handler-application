import { Injectable, inject } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { isPresent } from 'app/core/util/operators';
import { ApplicationConfigService } from 'app/core/config/application-config.service';
import { createRequestOption } from 'app/core/request/request-util';
import { ICommissionService, NewCommissionService } from '../commission-service.model';

export type PartialUpdateCommissionService = Partial<ICommissionService> & Pick<ICommissionService, 'id'>;

export type EntityResponseType = HttpResponse<ICommissionService>;
export type EntityArrayResponseType = HttpResponse<ICommissionService[]>;

@Injectable({ providedIn: 'root' })
export class CommissionServiceService {
  protected readonly http = inject(HttpClient);
  protected readonly applicationConfigService = inject(ApplicationConfigService);

  protected resourceUrl = this.applicationConfigService.getEndpointFor('api/commission-services');

  create(commissionService: NewCommissionService): Observable<EntityResponseType> {
    return this.http.post<ICommissionService>(this.resourceUrl, commissionService, { observe: 'response' });
  }

  update(commissionService: ICommissionService): Observable<EntityResponseType> {
    return this.http.put<ICommissionService>(
      `${this.resourceUrl}/${this.getCommissionServiceIdentifier(commissionService)}`,
      commissionService,
      { observe: 'response' },
    );
  }

  partialUpdate(commissionService: PartialUpdateCommissionService): Observable<EntityResponseType> {
    return this.http.patch<ICommissionService>(
      `${this.resourceUrl}/${this.getCommissionServiceIdentifier(commissionService)}`,
      commissionService,
      { observe: 'response' },
    );
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<ICommissionService>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<ICommissionService[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  getCommissionServiceIdentifier(commissionService: Pick<ICommissionService, 'id'>): number {
    return commissionService.id;
  }

  compareCommissionService(o1: Pick<ICommissionService, 'id'> | null, o2: Pick<ICommissionService, 'id'> | null): boolean {
    return o1 && o2 ? this.getCommissionServiceIdentifier(o1) === this.getCommissionServiceIdentifier(o2) : o1 === o2;
  }

  addCommissionServiceToCollectionIfMissing<Type extends Pick<ICommissionService, 'id'>>(
    commissionServiceCollection: Type[],
    ...commissionServicesToCheck: (Type | null | undefined)[]
  ): Type[] {
    const commissionServices: Type[] = commissionServicesToCheck.filter(isPresent);
    if (commissionServices.length > 0) {
      const commissionServiceCollectionIdentifiers = commissionServiceCollection.map(commissionServiceItem =>
        this.getCommissionServiceIdentifier(commissionServiceItem),
      );
      const commissionServicesToAdd = commissionServices.filter(commissionServiceItem => {
        const commissionServiceIdentifier = this.getCommissionServiceIdentifier(commissionServiceItem);
        if (commissionServiceCollectionIdentifiers.includes(commissionServiceIdentifier)) {
          return false;
        }
        commissionServiceCollectionIdentifiers.push(commissionServiceIdentifier);
        return true;
      });
      return [...commissionServicesToAdd, ...commissionServiceCollection];
    }
    return commissionServiceCollection;
  }
}
