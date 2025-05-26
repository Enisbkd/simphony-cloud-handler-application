import { Routes } from '@angular/router';

import { UserRouteAccessService } from 'app/core/auth/user-route-access.service';
import PointDeVenteResolve from './route/point-de-vente-routing-resolve.service';

const pointDeVenteRoute: Routes = [
  {
    path: '',
    loadComponent: () => import('./list/point-de-vente.component').then(m => m.PointDeVenteComponent),
    data: {},
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    loadComponent: () => import('./detail/point-de-vente-detail.component').then(m => m.PointDeVenteDetailComponent),
    resolve: {
      pointDeVente: PointDeVenteResolve,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    loadComponent: () => import('./update/point-de-vente-update.component').then(m => m.PointDeVenteUpdateComponent),
    resolve: {
      pointDeVente: PointDeVenteResolve,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    loadComponent: () => import('./update/point-de-vente-update.component').then(m => m.PointDeVenteUpdateComponent),
    resolve: {
      pointDeVente: PointDeVenteResolve,
    },
    canActivate: [UserRouteAccessService],
  },
];

export default pointDeVenteRoute;
