import { Routes } from '@angular/router';

import { UserRouteAccessService } from 'app/core/auth/user-route-access.service';
import ModePaiementResolve from './route/mode-paiement-routing-resolve.service';

const modePaiementRoute: Routes = [
  {
    path: '',
    loadComponent: () => import('./list/mode-paiement.component').then(m => m.ModePaiementComponent),
    data: {},
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    loadComponent: () => import('./detail/mode-paiement-detail.component').then(m => m.ModePaiementDetailComponent),
    resolve: {
      modePaiement: ModePaiementResolve,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    loadComponent: () => import('./update/mode-paiement-update.component').then(m => m.ModePaiementUpdateComponent),
    resolve: {
      modePaiement: ModePaiementResolve,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    loadComponent: () => import('./update/mode-paiement-update.component').then(m => m.ModePaiementUpdateComponent),
    resolve: {
      modePaiement: ModePaiementResolve,
    },
    canActivate: [UserRouteAccessService],
  },
];

export default modePaiementRoute;
