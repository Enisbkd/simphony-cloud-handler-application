import { Component, inject } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';

import SharedModule from 'app/shared/shared.module';
import { ITEM_DELETED_EVENT } from 'app/config/navigation.constants';
import { IMajorGroup } from '../major-group.model';
import { MajorGroupService } from '../service/major-group.service';

@Component({
  templateUrl: './major-group-delete-dialog.component.html',
  imports: [SharedModule, FormsModule],
})
export class MajorGroupDeleteDialogComponent {
  majorGroup?: IMajorGroup;

  protected majorGroupService = inject(MajorGroupService);
  protected activeModal = inject(NgbActiveModal);

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.majorGroupService.delete(id).subscribe(() => {
      this.activeModal.close(ITEM_DELETED_EVENT);
    });
  }
}
