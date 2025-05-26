import { Component, OnInit, inject } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize } from 'rxjs/operators';

import SharedModule from 'app/shared/shared.module';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';

import { IFactureDetail } from '../facture-detail.model';
import { FactureDetailService } from '../service/facture-detail.service';
import { FactureDetailFormGroup, FactureDetailFormService } from './facture-detail-form.service';

@Component({
  selector: 'jhi-facture-detail-update',
  templateUrl: './facture-detail-update.component.html',
  imports: [SharedModule, FormsModule, ReactiveFormsModule],
})
export class FactureDetailUpdateComponent implements OnInit {
  isSaving = false;
  factureDetail: IFactureDetail | null = null;

  protected factureDetailService = inject(FactureDetailService);
  protected factureDetailFormService = inject(FactureDetailFormService);
  protected activatedRoute = inject(ActivatedRoute);

  // eslint-disable-next-line @typescript-eslint/member-ordering
  editForm: FactureDetailFormGroup = this.factureDetailFormService.createFactureDetailFormGroup();

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ factureDetail }) => {
      this.factureDetail = factureDetail;
      if (factureDetail) {
        this.updateForm(factureDetail);
      }
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const factureDetail = this.factureDetailFormService.getFactureDetail(this.editForm);
    if (factureDetail.id !== null) {
      this.subscribeToSaveResponse(this.factureDetailService.update(factureDetail));
    } else {
      this.subscribeToSaveResponse(this.factureDetailService.create(factureDetail));
    }
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IFactureDetail>>): void {
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

  protected updateForm(factureDetail: IFactureDetail): void {
    this.factureDetail = factureDetail;
    this.factureDetailFormService.resetForm(this.editForm, factureDetail);
  }
}
