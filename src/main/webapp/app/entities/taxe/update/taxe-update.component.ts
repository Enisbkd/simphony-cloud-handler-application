import { Component, OnInit, inject } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize } from 'rxjs/operators';

import SharedModule from 'app/shared/shared.module';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';

import { ITaxe } from '../taxe.model';
import { TaxeService } from '../service/taxe.service';
import { TaxeFormGroup, TaxeFormService } from './taxe-form.service';

@Component({
  selector: 'jhi-taxe-update',
  templateUrl: './taxe-update.component.html',
  imports: [SharedModule, FormsModule, ReactiveFormsModule],
})
export class TaxeUpdateComponent implements OnInit {
  isSaving = false;
  taxe: ITaxe | null = null;

  protected taxeService = inject(TaxeService);
  protected taxeFormService = inject(TaxeFormService);
  protected activatedRoute = inject(ActivatedRoute);

  // eslint-disable-next-line @typescript-eslint/member-ordering
  editForm: TaxeFormGroup = this.taxeFormService.createTaxeFormGroup();

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ taxe }) => {
      this.taxe = taxe;
      if (taxe) {
        this.updateForm(taxe);
      }
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const taxe = this.taxeFormService.getTaxe(this.editForm);
    if (taxe.id !== null) {
      this.subscribeToSaveResponse(this.taxeService.update(taxe));
    } else {
      this.subscribeToSaveResponse(this.taxeService.create(taxe));
    }
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<ITaxe>>): void {
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

  protected updateForm(taxe: ITaxe): void {
    this.taxe = taxe;
    this.taxeFormService.resetForm(this.editForm, taxe);
  }
}
