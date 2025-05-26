import { Routes } from '@angular/router';

import { UserRouteAccessService } from 'app/core/auth/user-route-access.service';
import EtablissementResolve from './route/etablissement-routing-resolve.service';

const etablissementRoute: Routes = [
  {
    path: '',
    loadComponent: () => import('./list/etablissement.component').then(m => m.EtablissementComponent),
    data: {},
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    loadComponent: () => import('./detail/etablissement-detail.component').then(m => m.EtablissementDetailComponent),
    resolve: {
      etablissement: EtablissementResolve,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    loadComponent: () => import('./update/etablissement-update.component').then(m => m.EtablissementUpdateComponent),
    resolve: {
      etablissement: EtablissementResolve,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    loadComponent: () => import('./update/etablissement-update.component').then(m => m.EtablissementUpdateComponent),
    resolve: {
      etablissement: EtablissementResolve,
    },
    canActivate: [UserRouteAccessService],
  },
];

export default etablissementRoute;
