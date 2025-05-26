import { Component, inject } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';

import SharedModule from 'app/shared/shared.module';
import { ITEM_DELETED_EVENT } from 'app/config/navigation.constants';
import { IBarcode } from '../barcode.model';
import { BarcodeService } from '../service/barcode.service';

@Component({
  templateUrl: './barcode-delete-dialog.component.html',
  imports: [SharedModule, FormsModule],
})
export class BarcodeDeleteDialogComponent {
  barcode?: IBarcode;

  protected barcodeService = inject(BarcodeService);
  protected activeModal = inject(NgbActiveModal);

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.barcodeService.delete(id).subscribe(() => {
      this.activeModal.close(ITEM_DELETED_EVENT);
    });
  }
}
