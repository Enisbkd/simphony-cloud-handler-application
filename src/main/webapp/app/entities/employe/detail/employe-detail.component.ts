import { Component, input } from '@angular/core';
import { RouterModule } from '@angular/router';

import SharedModule from 'app/shared/shared.module';
import { IEmploye } from '../employe.model';

@Component({
  selector: 'jhi-employe-detail',
  templateUrl: './employe-detail.component.html',
  imports: [SharedModule, RouterModule],
})
export class EmployeDetailComponent {
  employe = input<IEmploye | null>(null);

  previousState(): void {
    window.history.back();
  }
}
