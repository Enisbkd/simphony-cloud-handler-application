import { Component, inject } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';

import SharedModule from 'app/shared/shared.module';
import { ITEM_DELETED_EVENT } from 'app/config/navigation.constants';
import { ITaxe } from '../taxe.model';
import { TaxeService } from '../service/taxe.service';

@Component({
  templateUrl: './taxe-delete-dialog.component.html',
  imports: [SharedModule, FormsModule],
})
export class TaxeDeleteDialogComponent {
  taxe?: ITaxe;

  protected taxeService = inject(TaxeService);
  protected activeModal = inject(NgbActiveModal);

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.taxeService.delete(id).subscribe(() => {
      this.activeModal.close(ITEM_DELETED_EVENT);
    });
  }
}
