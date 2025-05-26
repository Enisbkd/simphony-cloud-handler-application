import { Component, inject } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';

import SharedModule from 'app/shared/shared.module';
import { ITEM_DELETED_EVENT } from 'app/config/navigation.constants';
import { IPointDeVente } from '../point-de-vente.model';
import { PointDeVenteService } from '../service/point-de-vente.service';

@Component({
  templateUrl: './point-de-vente-delete-dialog.component.html',
  imports: [SharedModule, FormsModule],
})
export class PointDeVenteDeleteDialogComponent {
  pointDeVente?: IPointDeVente;

  protected pointDeVenteService = inject(PointDeVenteService);
  protected activeModal = inject(NgbActiveModal);

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.pointDeVenteService.delete(id).subscribe(() => {
      this.activeModal.close(ITEM_DELETED_EVENT);
    });
  }
}
