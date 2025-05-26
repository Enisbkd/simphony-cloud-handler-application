import { Component, input } from '@angular/core';
import { RouterModule } from '@angular/router';

import SharedModule from 'app/shared/shared.module';
import { IPartieDeJournee } from '../partie-de-journee.model';

@Component({
  selector: 'jhi-partie-de-journee-detail',
  templateUrl: './partie-de-journee-detail.component.html',
  imports: [SharedModule, RouterModule],
})
export class PartieDeJourneeDetailComponent {
  partieDeJournee = input<IPartieDeJournee | null>(null);

  previousState(): void {
    window.history.back();
  }
}
