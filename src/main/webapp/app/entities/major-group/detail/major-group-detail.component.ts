import { Component, input } from '@angular/core';
import { RouterModule } from '@angular/router';

import SharedModule from 'app/shared/shared.module';
import { IMajorGroup } from '../major-group.model';

@Component({
  selector: 'jhi-major-group-detail',
  templateUrl: './major-group-detail.component.html',
  imports: [SharedModule, RouterModule],
})
export class MajorGroupDetailComponent {
  majorGroup = input<IMajorGroup | null>(null);

  previousState(): void {
    window.history.back();
  }
}
