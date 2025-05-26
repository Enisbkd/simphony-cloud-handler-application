import { Routes } from '@angular/router';

import { UserRouteAccessService } from 'app/core/auth/user-route-access.service';
import MajorGroupResolve from './route/major-group-routing-resolve.service';

const majorGroupRoute: Routes = [
  {
    path: '',
    loadComponent: () => import('./list/major-group.component').then(m => m.MajorGroupComponent),
    data: {},
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    loadComponent: () => import('./detail/major-group-detail.component').then(m => m.MajorGroupDetailComponent),
    resolve: {
      majorGroup: MajorGroupResolve,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    loadComponent: () => import('./update/major-group-update.component').then(m => m.MajorGroupUpdateComponent),
    resolve: {
      majorGroup: MajorGroupResolve,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    loadComponent: () => import('./update/major-group-update.component').then(m => m.MajorGroupUpdateComponent),
    resolve: {
      majorGroup: MajorGroupResolve,
    },
    canActivate: [UserRouteAccessService],
  },
];

export default majorGroupRoute;
