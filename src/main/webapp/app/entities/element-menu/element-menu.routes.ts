import { Routes } from '@angular/router';

import { UserRouteAccessService } from 'app/core/auth/user-route-access.service';
import ElementMenuResolve from './route/element-menu-routing-resolve.service';

const elementMenuRoute: Routes = [
  {
    path: '',
    loadComponent: () => import('./list/element-menu.component').then(m => m.ElementMenuComponent),
    data: {},
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    loadComponent: () => import('./detail/element-menu-detail.component').then(m => m.ElementMenuDetailComponent),
    resolve: {
      elementMenu: ElementMenuResolve,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    loadComponent: () => import('./update/element-menu-update.component').then(m => m.ElementMenuUpdateComponent),
    resolve: {
      elementMenu: ElementMenuResolve,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    loadComponent: () => import('./update/element-menu-update.component').then(m => m.ElementMenuUpdateComponent),
    resolve: {
      elementMenu: ElementMenuResolve,
    },
    canActivate: [UserRouteAccessService],
  },
];

export default elementMenuRoute;
