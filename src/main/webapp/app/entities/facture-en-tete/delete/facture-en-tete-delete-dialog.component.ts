import { Component, inject } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';

import SharedModule from 'app/shared/shared.module';
import { ITEM_DELETED_EVENT } from 'app/config/navigation.constants';
import { IFactureEnTete } from '../facture-en-tete.model';
import { FactureEnTeteService } from '../service/facture-en-tete.service';

@Component({
  templateUrl: './facture-en-tete-delete-dialog.component.html',
  imports: [SharedModule, FormsModule],
})
export class FactureEnTeteDeleteDialogComponent {
  factureEnTete?: IFactureEnTete;

  protected factureEnTeteService = inject(FactureEnTeteService);
  protected activeModal = inject(NgbActiveModal);

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.factureEnTeteService.delete(id).subscribe(() => {
      this.activeModal.close(ITEM_DELETED_EVENT);
    });
  }
}
