import { Routes } from '@angular/router';

import { UserRouteAccessService } from 'app/core/auth/user-route-access.service';
import FactureEnTeteResolve from './route/facture-en-tete-routing-resolve.service';

const factureEnTeteRoute: Routes = [
  {
    path: '',
    loadComponent: () => import('./list/facture-en-tete.component').then(m => m.FactureEnTeteComponent),
    data: {},
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    loadComponent: () => import('./detail/facture-en-tete-detail.component').then(m => m.FactureEnTeteDetailComponent),
    resolve: {
      factureEnTete: FactureEnTeteResolve,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    loadComponent: () => import('./update/facture-en-tete-update.component').then(m => m.FactureEnTeteUpdateComponent),
    resolve: {
      factureEnTete: FactureEnTeteResolve,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    loadComponent: () => import('./update/facture-en-tete-update.component').then(m => m.FactureEnTeteUpdateComponent),
    resolve: {
      factureEnTete: FactureEnTeteResolve,
    },
    canActivate: [UserRouteAccessService],
  },
];

export default factureEnTeteRoute;
