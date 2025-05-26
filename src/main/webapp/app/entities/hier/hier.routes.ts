import { Routes } from '@angular/router';

import { UserRouteAccessService } from 'app/core/auth/user-route-access.service';
import HierResolve from './route/hier-routing-resolve.service';

const hierRoute: Routes = [
  {
    path: '',
    loadComponent: () => import('./list/hier.component').then(m => m.HierComponent),
    data: {},
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    loadComponent: () => import('./detail/hier-detail.component').then(m => m.HierDetailComponent),
    resolve: {
      hier: HierResolve,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    loadComponent: () => import('./update/hier-update.component').then(m => m.HierUpdateComponent),
    resolve: {
      hier: HierResolve,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    loadComponent: () => import('./update/hier-update.component').then(m => m.HierUpdateComponent),
    resolve: {
      hier: HierResolve,
    },
    canActivate: [UserRouteAccessService],
  },
];

export default hierRoute;
