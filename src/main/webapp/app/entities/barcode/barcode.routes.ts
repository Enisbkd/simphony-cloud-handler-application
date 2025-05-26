import { Routes } from '@angular/router';

import { UserRouteAccessService } from 'app/core/auth/user-route-access.service';
import BarcodeResolve from './route/barcode-routing-resolve.service';

const barcodeRoute: Routes = [
  {
    path: '',
    loadComponent: () => import('./list/barcode.component').then(m => m.BarcodeComponent),
    data: {},
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    loadComponent: () => import('./detail/barcode-detail.component').then(m => m.BarcodeDetailComponent),
    resolve: {
      barcode: BarcodeResolve,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    loadComponent: () => import('./update/barcode-update.component').then(m => m.BarcodeUpdateComponent),
    resolve: {
      barcode: BarcodeResolve,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    loadComponent: () => import('./update/barcode-update.component').then(m => m.BarcodeUpdateComponent),
    resolve: {
      barcode: BarcodeResolve,
    },
    canActivate: [UserRouteAccessService],
  },
];

export default barcodeRoute;
