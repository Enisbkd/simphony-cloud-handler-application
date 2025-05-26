import { Component, inject } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';

import SharedModule from 'app/shared/shared.module';
import { ITEM_DELETED_EVENT } from 'app/config/navigation.constants';
import { IFactureDetail } from '../facture-detail.model';
import { FactureDetailService } from '../service/facture-detail.service';

@Component({
  templateUrl: './facture-detail-delete-dialog.component.html',
  imports: [SharedModule, FormsModule],
})
export class FactureDetailDeleteDialogComponent {
  factureDetail?: IFactureDetail;

  protected factureDetailService = inject(FactureDetailService);
  protected activeModal = inject(NgbActiveModal);

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.factureDetailService.delete(id).subscribe(() => {
      this.activeModal.close(ITEM_DELETED_EVENT);
    });
  }
}
