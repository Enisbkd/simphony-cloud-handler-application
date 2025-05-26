import { Routes } from '@angular/router';

import { UserRouteAccessService } from 'app/core/auth/user-route-access.service';
import RemiseResolve from './route/remise-routing-resolve.service';

const remiseRoute: Routes = [
  {
    path: '',
    loadComponent: () => import('./list/remise.component').then(m => m.RemiseComponent),
    data: {},
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    loadComponent: () => import('./detail/remise-detail.component').then(m => m.RemiseDetailComponent),
    resolve: {
      remise: RemiseResolve,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    loadComponent: () => import('./update/remise-update.component').then(m => m.RemiseUpdateComponent),
    resolve: {
      remise: RemiseResolve,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    loadComponent: () => import('./update/remise-update.component').then(m => m.RemiseUpdateComponent),
    resolve: {
      remise: RemiseResolve,
    },
    canActivate: [UserRouteAccessService],
  },
];

export default remiseRoute;
