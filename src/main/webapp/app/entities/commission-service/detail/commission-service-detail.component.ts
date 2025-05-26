import { Component, input } from '@angular/core';
import { RouterModule } from '@angular/router';

import SharedModule from 'app/shared/shared.module';
import { ICommissionService } from '../commission-service.model';

@Component({
  selector: 'jhi-commission-service-detail',
  templateUrl: './commission-service-detail.component.html',
  imports: [SharedModule, RouterModule],
})
export class CommissionServiceDetailComponent {
  commissionService = input<ICommissionService | null>(null);

  previousState(): void {
    window.history.back();
  }
}
