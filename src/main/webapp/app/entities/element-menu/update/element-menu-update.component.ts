import { Component, OnInit, inject } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize } from 'rxjs/operators';

import SharedModule from 'app/shared/shared.module';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';

import { IElementMenu } from '../element-menu.model';
import { ElementMenuService } from '../service/element-menu.service';
import { ElementMenuFormGroup, ElementMenuFormService } from './element-menu-form.service';

@Component({
  selector: 'jhi-element-menu-update',
  templateUrl: './element-menu-update.component.html',
  imports: [SharedModule, FormsModule, ReactiveFormsModule],
})
export class ElementMenuUpdateComponent implements OnInit {
  isSaving = false;
  elementMenu: IElementMenu | null = null;

  protected elementMenuService = inject(ElementMenuService);
  protected elementMenuFormService = inject(ElementMenuFormService);
  protected activatedRoute = inject(ActivatedRoute);

  // eslint-disable-next-line @typescript-eslint/member-ordering
  editForm: ElementMenuFormGroup = this.elementMenuFormService.createElementMenuFormGroup();

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ elementMenu }) => {
      this.elementMenu = elementMenu;
      if (elementMenu) {
        this.updateForm(elementMenu);
      }
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const elementMenu = this.elementMenuFormService.getElementMenu(this.editForm);
    if (elementMenu.id !== null) {
      this.subscribeToSaveResponse(this.elementMenuService.update(elementMenu));
    } else {
      this.subscribeToSaveResponse(this.elementMenuService.create(elementMenu));
    }
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IElementMenu>>): void {
    result.pipe(finalize(() => this.onSaveFinalize())).subscribe({
      next: () => this.onSaveSuccess(),
      error: () => this.onSaveError(),
    });
  }

  protected onSaveSuccess(): void {
    this.previousState();
  }

  protected onSaveError(): void {
    // Api for inheritance.
  }

  protected onSaveFinalize(): void {
    this.isSaving = false;
  }

  protected updateForm(elementMenu: IElementMenu): void {
    this.elementMenu = elementMenu;
    this.elementMenuFormService.resetForm(this.editForm, elementMenu);
  }
}
