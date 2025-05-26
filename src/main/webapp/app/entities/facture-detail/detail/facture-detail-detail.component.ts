import { Component, input } from '@angular/core';
import { RouterModule } from '@angular/router';

import SharedModule from 'app/shared/shared.module';
import { FormatMediumDatetimePipe } from 'app/shared/date';
import { IFactureDetail } from '../facture-detail.model';

@Component({
  selector: 'jhi-facture-detail-detail',
  templateUrl: './facture-detail-detail.component.html',
  imports: [SharedModule, RouterModule, FormatMediumDatetimePipe],
})
export class FactureDetailDetailComponent {
  factureDetail = input<IFactureDetail | null>(null);

  previousState(): void {
    window.history.back();
  }
}
