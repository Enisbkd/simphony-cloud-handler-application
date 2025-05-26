import { Component, input } from '@angular/core';
import { RouterModule } from '@angular/router';

import SharedModule from 'app/shared/shared.module';
import { FormatMediumDatetimePipe } from 'app/shared/date';
import { IFactureEnTete } from '../facture-en-tete.model';

@Component({
  selector: 'jhi-facture-en-tete-detail',
  templateUrl: './facture-en-tete-detail.component.html',
  imports: [SharedModule, RouterModule, FormatMediumDatetimePipe],
})
export class FactureEnTeteDetailComponent {
  factureEnTete = input<IFactureEnTete | null>(null);

  previousState(): void {
    window.history.back();
  }
}
