import { Component, OnInit, inject } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize } from 'rxjs/operators';

import SharedModule from 'app/shared/shared.module';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';

import { IHier } from '../hier.model';
import { HierService } from '../service/hier.service';
import { HierFormGroup, HierFormService } from './hier-form.service';

@Component({
  selector: 'jhi-hier-update',
  templateUrl: './hier-update.component.html',
  imports: [SharedModule, FormsModule, ReactiveFormsModule],
})
export class HierUpdateComponent implements OnInit {
  isSaving = false;
  hier: IHier | null = null;

  protected hierService = inject(HierService);
  protected hierFormService = inject(HierFormService);
  protected activatedRoute = inject(ActivatedRoute);

  // eslint-disable-next-line @typescript-eslint/member-ordering
  editForm: HierFormGroup = this.hierFormService.createHierFormGroup();

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ hier }) => {
      this.hier = hier;
      if (hier) {
        this.updateForm(hier);
      }
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const hier = this.hierFormService.getHier(this.editForm);
    if (hier.id !== null) {
      this.subscribeToSaveResponse(this.hierService.update(hier));
    } else {
      this.subscribeToSaveResponse(this.hierService.create(hier));
    }
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IHier>>): void {
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

  protected updateForm(hier: IHier): void {
    this.hier = hier;
    this.hierFormService.resetForm(this.editForm, hier);
  }
}
