import { Component, input } from '@angular/core';
import { RouterModule } from '@angular/router';

import SharedModule from 'app/shared/shared.module';
import { ICodeRaison } from '../code-raison.model';

@Component({
  selector: 'jhi-code-raison-detail',
  templateUrl: './code-raison-detail.component.html',
  imports: [SharedModule, RouterModule],
})
export class CodeRaisonDetailComponent {
  codeRaison = input<ICodeRaison | null>(null);

  previousState(): void {
    window.history.back();
  }
}
