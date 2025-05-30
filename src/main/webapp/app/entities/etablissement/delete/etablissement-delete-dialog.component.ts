import { Component, inject } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';

import SharedModule from 'app/shared/shared.module';
import { ITEM_DELETED_EVENT } from 'app/config/navigation.constants';
import { IEtablissement } from '../etablissement.model';
import { EtablissementService } from '../service/etablissement.service';

@Component({
  templateUrl: './etablissement-delete-dialog.component.html',
  imports: [SharedModule, FormsModule],
})
export class EtablissementDeleteDialogComponent {
  etablissement?: IEtablissement;

  protected etablissementService = inject(EtablissementService);
  protected activeModal = inject(NgbActiveModal);

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: string): void {
    this.etablissementService.delete(id).subscribe(() => {
      this.activeModal.close(ITEM_DELETED_EVENT);
    });
  }
}
