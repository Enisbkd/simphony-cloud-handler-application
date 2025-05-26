import { Component, OnInit, inject } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize } from 'rxjs/operators';

import SharedModule from 'app/shared/shared.module';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';

import { IBarcode } from '../barcode.model';
import { BarcodeService } from '../service/barcode.service';
import { BarcodeFormGroup, BarcodeFormService } from './barcode-form.service';

@Component({
  selector: 'jhi-barcode-update',
  templateUrl: './barcode-update.component.html',
  imports: [SharedModule, FormsModule, ReactiveFormsModule],
})
export class BarcodeUpdateComponent implements OnInit {
  isSaving = false;
  barcode: IBarcode | null = null;

  protected barcodeService = inject(BarcodeService);
  protected barcodeFormService = inject(BarcodeFormService);
  protected activatedRoute = inject(ActivatedRoute);

  // eslint-disable-next-line @typescript-eslint/member-ordering
  editForm: BarcodeFormGroup = this.barcodeFormService.createBarcodeFormGroup();

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ barcode }) => {
      this.barcode = barcode;
      if (barcode) {
        this.updateForm(barcode);
      }
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const barcode = this.barcodeFormService.getBarcode(this.editForm);
    if (barcode.id !== null) {
      this.subscribeToSaveResponse(this.barcodeService.update(barcode));
    } else {
      this.subscribeToSaveResponse(this.barcodeService.create(barcode));
    }
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IBarcode>>): void {
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

  protected updateForm(barcode: IBarcode): void {
    this.barcode = barcode;
    this.barcodeFormService.resetForm(this.editForm, barcode);
  }
}
