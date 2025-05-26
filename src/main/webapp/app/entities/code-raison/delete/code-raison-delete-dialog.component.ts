import { Component, inject } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';

import SharedModule from 'app/shared/shared.module';
import { ITEM_DELETED_EVENT } from 'app/config/navigation.constants';
import { ICodeRaison } from '../code-raison.model';
import { CodeRaisonService } from '../service/code-raison.service';

@Component({
  templateUrl: './code-raison-delete-dialog.component.html',
  imports: [SharedModule, FormsModule],
})
export class CodeRaisonDeleteDialogComponent {
  codeRaison?: ICodeRaison;

  protected codeRaisonService = inject(CodeRaisonService);
  protected activeModal = inject(NgbActiveModal);

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.codeRaisonService.delete(id).subscribe(() => {
      this.activeModal.close(ITEM_DELETED_EVENT);
    });
  }
}
