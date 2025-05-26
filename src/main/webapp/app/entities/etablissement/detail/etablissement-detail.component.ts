import { Component, input } from '@angular/core';
import { RouterModule } from '@angular/router';

import SharedModule from 'app/shared/shared.module';
import { IEtablissement } from '../etablissement.model';

@Component({
  selector: 'jhi-etablissement-detail',
  templateUrl: './etablissement-detail.component.html',
  imports: [SharedModule, RouterModule],
})
export class EtablissementDetailComponent {
  etablissement = input<IEtablissement | null>(null);

  previousState(): void {
    window.history.back();
  }
}
