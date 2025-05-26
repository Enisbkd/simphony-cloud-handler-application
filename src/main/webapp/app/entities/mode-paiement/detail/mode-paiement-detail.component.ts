import { Component, input } from '@angular/core';
import { RouterModule } from '@angular/router';

import SharedModule from 'app/shared/shared.module';
import { IModePaiement } from '../mode-paiement.model';

@Component({
  selector: 'jhi-mode-paiement-detail',
  templateUrl: './mode-paiement-detail.component.html',
  imports: [SharedModule, RouterModule],
})
export class ModePaiementDetailComponent {
  modePaiement = input<IModePaiement | null>(null);

  previousState(): void {
    window.history.back();
  }
}
