import { Component, input } from '@angular/core';
import { RouterModule } from '@angular/router';

import SharedModule from 'app/shared/shared.module';
import { IFamilyGroup } from '../family-group.model';

@Component({
  selector: 'jhi-family-group-detail',
  templateUrl: './family-group-detail.component.html',
  imports: [SharedModule, RouterModule],
})
export class FamilyGroupDetailComponent {
  familyGroup = input<IFamilyGroup | null>(null);

  previousState(): void {
    window.history.back();
  }
}
