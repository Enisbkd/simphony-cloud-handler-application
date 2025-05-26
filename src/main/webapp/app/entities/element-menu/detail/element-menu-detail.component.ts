import { Component, input } from '@angular/core';
import { RouterModule } from '@angular/router';

import SharedModule from 'app/shared/shared.module';
import { IElementMenu } from '../element-menu.model';

@Component({
  selector: 'jhi-element-menu-detail',
  templateUrl: './element-menu-detail.component.html',
  imports: [SharedModule, RouterModule],
})
export class ElementMenuDetailComponent {
  elementMenu = input<IElementMenu | null>(null);

  previousState(): void {
    window.history.back();
  }
}
