import { Component, inject } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';

import SharedModule from 'app/shared/shared.module';
import { ITEM_DELETED_EVENT } from 'app/config/navigation.constants';
import { ICategoriePointDeVente } from '../categorie-point-de-vente.model';
import { CategoriePointDeVenteService } from '../service/categorie-point-de-vente.service';

@Component({
  templateUrl: './categorie-point-de-vente-delete-dialog.component.html',
  imports: [SharedModule, FormsModule],
})
export class CategoriePointDeVenteDeleteDialogComponent {
  categoriePointDeVente?: ICategoriePointDeVente;

  protected categoriePointDeVenteService = inject(CategoriePointDeVenteService);
  protected activeModal = inject(NgbActiveModal);

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.categoriePointDeVenteService.delete(id).subscribe(() => {
      this.activeModal.close(ITEM_DELETED_EVENT);
    });
  }
}
