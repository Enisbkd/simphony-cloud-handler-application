import { Routes } from '@angular/router';

import { UserRouteAccessService } from 'app/core/auth/user-route-access.service';
import PartieDeJourneeResolve from './route/partie-de-journee-routing-resolve.service';

const partieDeJourneeRoute: Routes = [
  {
    path: '',
    loadComponent: () => import('./list/partie-de-journee.component').then(m => m.PartieDeJourneeComponent),
    data: {},
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    loadComponent: () => import('./detail/partie-de-journee-detail.component').then(m => m.PartieDeJourneeDetailComponent),
    resolve: {
      partieDeJournee: PartieDeJourneeResolve,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    loadComponent: () => import('./update/partie-de-journee-update.component').then(m => m.PartieDeJourneeUpdateComponent),
    resolve: {
      partieDeJournee: PartieDeJourneeResolve,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    loadComponent: () => import('./update/partie-de-journee-update.component').then(m => m.PartieDeJourneeUpdateComponent),
    resolve: {
      partieDeJournee: PartieDeJourneeResolve,
    },
    canActivate: [UserRouteAccessService],
  },
];

export default partieDeJourneeRoute;
