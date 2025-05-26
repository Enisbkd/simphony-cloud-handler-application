import { Routes } from '@angular/router';

import { UserRouteAccessService } from 'app/core/auth/user-route-access.service';
import CategoriePointDeVenteResolve from './route/categorie-point-de-vente-routing-resolve.service';

const categoriePointDeVenteRoute: Routes = [
  {
    path: '',
    loadComponent: () => import('./list/categorie-point-de-vente.component').then(m => m.CategoriePointDeVenteComponent),
    data: {},
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    loadComponent: () => import('./detail/categorie-point-de-vente-detail.component').then(m => m.CategoriePointDeVenteDetailComponent),
    resolve: {
      categoriePointDeVente: CategoriePointDeVenteResolve,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    loadComponent: () => import('./update/categorie-point-de-vente-update.component').then(m => m.CategoriePointDeVenteUpdateComponent),
    resolve: {
      categoriePointDeVente: CategoriePointDeVenteResolve,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    loadComponent: () => import('./update/categorie-point-de-vente-update.component').then(m => m.CategoriePointDeVenteUpdateComponent),
    resolve: {
      categoriePointDeVente: CategoriePointDeVenteResolve,
    },
    canActivate: [UserRouteAccessService],
  },
];

export default categoriePointDeVenteRoute;
