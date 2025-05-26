import { Component, OnInit, inject } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize } from 'rxjs/operators';

import SharedModule from 'app/shared/shared.module';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';

import { IPartieDeJournee } from '../partie-de-journee.model';
import { PartieDeJourneeService } from '../service/partie-de-journee.service';
import { PartieDeJourneeFormGroup, PartieDeJourneeFormService } from './partie-de-journee-form.service';

@Component({
  selector: 'jhi-partie-de-journee-update',
  templateUrl: './partie-de-journee-update.component.html',
  imports: [SharedModule, FormsModule, ReactiveFormsModule],
})
export class PartieDeJourneeUpdateComponent implements OnInit {
  isSaving = false;
  partieDeJournee: IPartieDeJournee | null = null;

  protected partieDeJourneeService = inject(PartieDeJourneeService);
  protected partieDeJourneeFormService = inject(PartieDeJourneeFormService);
  protected activatedRoute = inject(ActivatedRoute);

  // eslint-disable-next-line @typescript-eslint/member-ordering
  editForm: PartieDeJourneeFormGroup = this.partieDeJourneeFormService.createPartieDeJourneeFormGroup();

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ partieDeJournee }) => {
      this.partieDeJournee = partieDeJournee;
      if (partieDeJournee) {
        this.updateForm(partieDeJournee);
      }
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const partieDeJournee = this.partieDeJourneeFormService.getPartieDeJournee(this.editForm);
    if (partieDeJournee.id !== null) {
      this.subscribeToSaveResponse(this.partieDeJourneeService.update(partieDeJournee));
    } else {
      this.subscribeToSaveResponse(this.partieDeJourneeService.create(partieDeJournee));
    }
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IPartieDeJournee>>): void {
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

  protected updateForm(partieDeJournee: IPartieDeJournee): void {
    this.partieDeJournee = partieDeJournee;
    this.partieDeJourneeFormService.resetForm(this.editForm, partieDeJournee);
  }
}
