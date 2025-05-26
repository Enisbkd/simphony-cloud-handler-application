import { Routes } from '@angular/router';

import { UserRouteAccessService } from 'app/core/auth/user-route-access.service';
import TaxeResolve from './route/taxe-routing-resolve.service';

const taxeRoute: Routes = [
  {
    path: '',
    loadComponent: () => import('./list/taxe.component').then(m => m.TaxeComponent),
    data: {},
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    loadComponent: () => import('./detail/taxe-detail.component').then(m => m.TaxeDetailComponent),
    resolve: {
      taxe: TaxeResolve,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    loadComponent: () => import('./update/taxe-update.component').then(m => m.TaxeUpdateComponent),
    resolve: {
      taxe: TaxeResolve,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    loadComponent: () => import('./update/taxe-update.component').then(m => m.TaxeUpdateComponent),
    resolve: {
      taxe: TaxeResolve,
    },
    canActivate: [UserRouteAccessService],
  },
];

export default taxeRoute;
