import { Component, inject } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';

import SharedModule from 'app/shared/shared.module';
import { ITEM_DELETED_EVENT } from 'app/config/navigation.constants';
import { IRemise } from '../remise.model';
import { RemiseService } from '../service/remise.service';

@Component({
  templateUrl: './remise-delete-dialog.component.html',
  imports: [SharedModule, FormsModule],
})
export class RemiseDeleteDialogComponent {
  remise?: IRemise;

  protected remiseService = inject(RemiseService);
  protected activeModal = inject(NgbActiveModal);

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.remiseService.delete(id).subscribe(() => {
      this.activeModal.close(ITEM_DELETED_EVENT);
    });
  }
}
