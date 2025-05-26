import { Component, inject } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';

import SharedModule from 'app/shared/shared.module';
import { ITEM_DELETED_EVENT } from 'app/config/navigation.constants';
import { IModePaiement } from '../mode-paiement.model';
import { ModePaiementService } from '../service/mode-paiement.service';

@Component({
  templateUrl: './mode-paiement-delete-dialog.component.html',
  imports: [SharedModule, FormsModule],
})
export class ModePaiementDeleteDialogComponent {
  modePaiement?: IModePaiement;

  protected modePaiementService = inject(ModePaiementService);
  protected activeModal = inject(NgbActiveModal);

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.modePaiementService.delete(id).subscribe(() => {
      this.activeModal.close(ITEM_DELETED_EVENT);
    });
  }
}
