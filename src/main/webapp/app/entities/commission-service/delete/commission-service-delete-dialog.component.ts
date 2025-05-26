import { Component, inject } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';

import SharedModule from 'app/shared/shared.module';
import { ITEM_DELETED_EVENT } from 'app/config/navigation.constants';
import { ICommissionService } from '../commission-service.model';
import { CommissionServiceService } from '../service/commission-service.service';

@Component({
  templateUrl: './commission-service-delete-dialog.component.html',
  imports: [SharedModule, FormsModule],
})
export class CommissionServiceDeleteDialogComponent {
  commissionService?: ICommissionService;

  protected commissionServiceService = inject(CommissionServiceService);
  protected activeModal = inject(NgbActiveModal);

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.commissionServiceService.delete(id).subscribe(() => {
      this.activeModal.close(ITEM_DELETED_EVENT);
    });
  }
}
