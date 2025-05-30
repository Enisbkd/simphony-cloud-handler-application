import { Component, input } from '@angular/core';
import { RouterModule } from '@angular/router';

import SharedModule from 'app/shared/shared.module';
import { IBarcode } from '../barcode.model';

@Component({
  selector: 'jhi-barcode-detail',
  templateUrl: './barcode-detail.component.html',
  imports: [SharedModule, RouterModule],
})
export class BarcodeDetailComponent {
  barcode = input<IBarcode | null>(null);

  previousState(): void {
    window.history.back();
  }
}
