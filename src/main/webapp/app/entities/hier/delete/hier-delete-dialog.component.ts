import { Component, inject } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';

import SharedModule from 'app/shared/shared.module';
import { ITEM_DELETED_EVENT } from 'app/config/navigation.constants';
import { IHier } from '../hier.model';
import { HierService } from '../service/hier.service';

@Component({
  templateUrl: './hier-delete-dialog.component.html',
  imports: [SharedModule, FormsModule],
})
export class HierDeleteDialogComponent {
  hier?: IHier;

  protected hierService = inject(HierService);
  protected activeModal = inject(NgbActiveModal);

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: string): void {
    this.hierService.delete(id).subscribe(() => {
      this.activeModal.close(ITEM_DELETED_EVENT);
    });
  }
}
