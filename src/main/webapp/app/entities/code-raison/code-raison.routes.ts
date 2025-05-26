import { Routes } from '@angular/router';

import { UserRouteAccessService } from 'app/core/auth/user-route-access.service';
import CodeRaisonResolve from './route/code-raison-routing-resolve.service';

const codeRaisonRoute: Routes = [
  {
    path: '',
    loadComponent: () => import('./list/code-raison.component').then(m => m.CodeRaisonComponent),
    data: {},
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    loadComponent: () => import('./detail/code-raison-detail.component').then(m => m.CodeRaisonDetailComponent),
    resolve: {
      codeRaison: CodeRaisonResolve,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    loadComponent: () => import('./update/code-raison-update.component').then(m => m.CodeRaisonUpdateComponent),
    resolve: {
      codeRaison: CodeRaisonResolve,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    loadComponent: () => import('./update/code-raison-update.component').then(m => m.CodeRaisonUpdateComponent),
    resolve: {
      codeRaison: CodeRaisonResolve,
    },
    canActivate: [UserRouteAccessService],
  },
];

export default codeRaisonRoute;
