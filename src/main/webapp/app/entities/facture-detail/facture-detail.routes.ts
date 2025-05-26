import { Routes } from '@angular/router';

import { UserRouteAccessService } from 'app/core/auth/user-route-access.service';
import FactureDetailResolve from './route/facture-detail-routing-resolve.service';

const factureDetailRoute: Routes = [
  {
    path: '',
    loadComponent: () => import('./list/facture-detail.component').then(m => m.FactureDetailComponent),
    data: {},
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    loadComponent: () => import('./detail/facture-detail-detail.component').then(m => m.FactureDetailDetailComponent),
    resolve: {
      factureDetail: FactureDetailResolve,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    loadComponent: () => import('./update/facture-detail-update.component').then(m => m.FactureDetailUpdateComponent),
    resolve: {
      factureDetail: FactureDetailResolve,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    loadComponent: () => import('./update/facture-detail-update.component').then(m => m.FactureDetailUpdateComponent),
    resolve: {
      factureDetail: FactureDetailResolve,
    },
    canActivate: [UserRouteAccessService],
  },
];

export default factureDetailRoute;
