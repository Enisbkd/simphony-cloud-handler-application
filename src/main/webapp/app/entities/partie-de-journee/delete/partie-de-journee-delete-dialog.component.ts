import { Component, inject } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';

import SharedModule from 'app/shared/shared.module';
import { ITEM_DELETED_EVENT } from 'app/config/navigation.constants';
import { IPartieDeJournee } from '../partie-de-journee.model';
import { PartieDeJourneeService } from '../service/partie-de-journee.service';

@Component({
  templateUrl: './partie-de-journee-delete-dialog.component.html',
  imports: [SharedModule, FormsModule],
})
export class PartieDeJourneeDeleteDialogComponent {
  partieDeJournee?: IPartieDeJournee;

  protected partieDeJourneeService = inject(PartieDeJourneeService);
  protected activeModal = inject(NgbActiveModal);

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.partieDeJourneeService.delete(id).subscribe(() => {
      this.activeModal.close(ITEM_DELETED_EVENT);
    });
  }
}
