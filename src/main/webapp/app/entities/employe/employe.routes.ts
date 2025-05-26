import { Routes } from '@angular/router';

import { UserRouteAccessService } from 'app/core/auth/user-route-access.service';
import EmployeResolve from './route/employe-routing-resolve.service';

const employeRoute: Routes = [
  {
    path: '',
    loadComponent: () => import('./list/employe.component').then(m => m.EmployeComponent),
    data: {},
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    loadComponent: () => import('./detail/employe-detail.component').then(m => m.EmployeDetailComponent),
    resolve: {
      employe: EmployeResolve,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    loadComponent: () => import('./update/employe-update.component').then(m => m.EmployeUpdateComponent),
    resolve: {
      employe: EmployeResolve,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    loadComponent: () => import('./update/employe-update.component').then(m => m.EmployeUpdateComponent),
    resolve: {
      employe: EmployeResolve,
    },
    canActivate: [UserRouteAccessService],
  },
];

export default employeRoute;
