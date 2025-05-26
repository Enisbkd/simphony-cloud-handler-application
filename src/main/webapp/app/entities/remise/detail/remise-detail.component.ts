import { Component, input } from '@angular/core';
import { RouterModule } from '@angular/router';

import SharedModule from 'app/shared/shared.module';
import { IRemise } from '../remise.model';

@Component({
  selector: 'jhi-remise-detail',
  templateUrl: './remise-detail.component.html',
  imports: [SharedModule, RouterModule],
})
export class RemiseDetailComponent {
  remise = input<IRemise | null>(null);

  previousState(): void {
    window.history.back();
  }
}
