import { Routes } from '@angular/router';

import { UserRouteAccessService } from 'app/core/auth/user-route-access.service';
import CommissionServiceResolve from './route/commission-service-routing-resolve.service';

const commissionServiceRoute: Routes = [
  {
    path: '',
    loadComponent: () => import('./list/commission-service.component').then(m => m.CommissionServiceComponent),
    data: {},
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    loadComponent: () => import('./detail/commission-service-detail.component').then(m => m.CommissionServiceDetailComponent),
    resolve: {
      commissionService: CommissionServiceResolve,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    loadComponent: () => import('./update/commission-service-update.component').then(m => m.CommissionServiceUpdateComponent),
    resolve: {
      commissionService: CommissionServiceResolve,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    loadComponent: () => import('./update/commission-service-update.component').then(m => m.CommissionServiceUpdateComponent),
    resolve: {
      commissionService: CommissionServiceResolve,
    },
    canActivate: [UserRouteAccessService],
  },
];

export default commissionServiceRoute;
