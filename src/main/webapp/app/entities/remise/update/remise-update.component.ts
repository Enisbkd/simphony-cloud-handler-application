import { Component, OnInit, inject } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize } from 'rxjs/operators';

import SharedModule from 'app/shared/shared.module';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';

import { IRemise } from '../remise.model';
import { RemiseService } from '../service/remise.service';
import { RemiseFormGroup, RemiseFormService } from './remise-form.service';

@Component({
  selector: 'jhi-remise-update',
  templateUrl: './remise-update.component.html',
  imports: [SharedModule, FormsModule, ReactiveFormsModule],
})
export class RemiseUpdateComponent implements OnInit {
  isSaving = false;
  remise: IRemise | null = null;

  protected remiseService = inject(RemiseService);
  protected remiseFormService = inject(RemiseFormService);
  protected activatedRoute = inject(ActivatedRoute);

  // eslint-disable-next-line @typescript-eslint/member-ordering
  editForm: RemiseFormGroup = this.remiseFormService.createRemiseFormGroup();

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ remise }) => {
      this.remise = remise;
      if (remise) {
        this.updateForm(remise);
      }
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const remise = this.remiseFormService.getRemise(this.editForm);
    if (remise.id !== null) {
      this.subscribeToSaveResponse(this.remiseService.update(remise));
    } else {
      this.subscribeToSaveResponse(this.remiseService.create(remise));
    }
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IRemise>>): void {
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

  protected updateForm(remise: IRemise): void {
    this.remise = remise;
    this.remiseFormService.resetForm(this.editForm, remise);
  }
}
