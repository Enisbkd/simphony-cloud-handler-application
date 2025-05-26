import { Routes } from '@angular/router';

import { UserRouteAccessService } from 'app/core/auth/user-route-access.service';
import FamilyGroupResolve from './route/family-group-routing-resolve.service';

const familyGroupRoute: Routes = [
  {
    path: '',
    loadComponent: () => import('./list/family-group.component').then(m => m.FamilyGroupComponent),
    data: {},
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    loadComponent: () => import('./detail/family-group-detail.component').then(m => m.FamilyGroupDetailComponent),
    resolve: {
      familyGroup: FamilyGroupResolve,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    loadComponent: () => import('./update/family-group-update.component').then(m => m.FamilyGroupUpdateComponent),
    resolve: {
      familyGroup: FamilyGroupResolve,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    loadComponent: () => import('./update/family-group-update.component').then(m => m.FamilyGroupUpdateComponent),
    resolve: {
      familyGroup: FamilyGroupResolve,
    },
    canActivate: [UserRouteAccessService],
  },
];

export default familyGroupRoute;
