import { Routes } from '@angular/router';

import { UserRouteAccessService } from 'app/core/auth/user-route-access.service';
import SocieteResolve from './route/societe-routing-resolve.service';

const societeRoute: Routes = [
  {
    path: '',
    loadComponent: () => import('./list/societe.component').then(m => m.SocieteComponent),
    data: {},
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    loadComponent: () => import('./detail/societe-detail.component').then(m => m.SocieteDetailComponent),
    resolve: {
      societe: SocieteResolve,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    loadComponent: () => import('./update/societe-update.component').then(m => m.SocieteUpdateComponent),
    resolve: {
      societe: SocieteResolve,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    loadComponent: () => import('./update/societe-update.component').then(m => m.SocieteUpdateComponent),
    resolve: {
      societe: SocieteResolve,
    },
    canActivate: [UserRouteAccessService],
  },
];

export default societeRoute;
