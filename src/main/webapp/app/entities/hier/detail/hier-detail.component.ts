import { Component, input } from '@angular/core';
import { RouterModule } from '@angular/router';

import SharedModule from 'app/shared/shared.module';
import { IHier } from '../hier.model';

@Component({
  selector: 'jhi-hier-detail',
  templateUrl: './hier-detail.component.html',
  imports: [SharedModule, RouterModule],
})
export class HierDetailComponent {
  hier = input<IHier | null>(null);

  previousState(): void {
    window.history.back();
  }
}
