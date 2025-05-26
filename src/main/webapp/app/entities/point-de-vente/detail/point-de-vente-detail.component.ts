import { Component, input } from '@angular/core';
import { RouterModule } from '@angular/router';

import SharedModule from 'app/shared/shared.module';
import { IPointDeVente } from '../point-de-vente.model';

@Component({
  selector: 'jhi-point-de-vente-detail',
  templateUrl: './point-de-vente-detail.component.html',
  imports: [SharedModule, RouterModule],
})
export class PointDeVenteDetailComponent {
  pointDeVente = input<IPointDeVente | null>(null);

  previousState(): void {
    window.history.back();
  }
}
