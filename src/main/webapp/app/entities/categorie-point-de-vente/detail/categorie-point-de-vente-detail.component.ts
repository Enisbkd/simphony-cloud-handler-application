import { Component, input } from '@angular/core';
import { RouterModule } from '@angular/router';

import SharedModule from 'app/shared/shared.module';
import { ICategoriePointDeVente } from '../categorie-point-de-vente.model';

@Component({
  selector: 'jhi-categorie-point-de-vente-detail',
  templateUrl: './categorie-point-de-vente-detail.component.html',
  imports: [SharedModule, RouterModule],
})
export class CategoriePointDeVenteDetailComponent {
  categoriePointDeVente = input<ICategoriePointDeVente | null>(null);

  previousState(): void {
    window.history.back();
  }
}
