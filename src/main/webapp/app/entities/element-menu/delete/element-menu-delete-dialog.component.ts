import { Component, inject } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';

import SharedModule from 'app/shared/shared.module';
import { ITEM_DELETED_EVENT } from 'app/config/navigation.constants';
import { IElementMenu } from '../element-menu.model';
import { ElementMenuService } from '../service/element-menu.service';

@Component({
  templateUrl: './element-menu-delete-dialog.component.html',
  imports: [SharedModule, FormsModule],
})
export class ElementMenuDeleteDialogComponent {
  elementMenu?: IElementMenu;

  protected elementMenuService = inject(ElementMenuService);
  protected activeModal = inject(NgbActiveModal);

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.elementMenuService.delete(id).subscribe(() => {
      this.activeModal.close(ITEM_DELETED_EVENT);
    });
  }
}
