import { Component, input } from '@angular/core';
import { RouterModule } from '@angular/router';

import SharedModule from 'app/shared/shared.module';
import { ITaxe } from '../taxe.model';

@Component({
  selector: 'jhi-taxe-detail',
  templateUrl: './taxe-detail.component.html',
  imports: [SharedModule, RouterModule],
})
export class TaxeDetailComponent {
  taxe = input<ITaxe | null>(null);

  previousState(): void {
    window.history.back();
  }
}
