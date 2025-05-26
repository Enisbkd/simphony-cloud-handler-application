import { Component, OnInit, inject } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize } from 'rxjs/operators';

import SharedModule from 'app/shared/shared.module';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';

import { ICommissionService } from '../commission-service.model';
import { CommissionServiceService } from '../service/commission-service.service';
import { CommissionServiceFormGroup, CommissionServiceFormService } from './commission-service-form.service';

@Component({
  selector: 'jhi-commission-service-update',
  templateUrl: './commission-service-update.component.html',
  imports: [SharedModule, FormsModule, ReactiveFormsModule],
})
export class CommissionServiceUpdateComponent implements OnInit {
  isSaving = false;
  commissionService: ICommissionService | null = null;

  protected commissionServiceService = inject(CommissionServiceService);
  protected commissionServiceFormService = inject(CommissionServiceFormService);
  protected activatedRoute = inject(ActivatedRoute);

  // eslint-disable-next-line @typescript-eslint/member-ordering
  editForm: CommissionServiceFormGroup = this.commissionServiceFormService.createCommissionServiceFormGroup();

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ commissionService }) => {
      this.commissionService = commissionService;
      if (commissionService) {
        this.updateForm(commissionService);
      }
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const commissionService = this.commissionServiceFormService.getCommissionService(this.editForm);
    if (commissionService.id !== null) {
      this.subscribeToSaveResponse(this.commissionServiceService.update(commissionService));
    } else {
      this.subscribeToSaveResponse(this.commissionServiceService.create(commissionService));
    }
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<ICommissionService>>): void {
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

  protected updateForm(commissionService: ICommissionService): void {
    this.commissionService = commissionService;
    this.commissionServiceFormService.resetForm(this.editForm, commissionService);
  }
}
