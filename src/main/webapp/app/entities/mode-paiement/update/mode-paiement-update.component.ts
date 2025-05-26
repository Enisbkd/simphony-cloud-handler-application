import { Component, OnInit, inject } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize } from 'rxjs/operators';

import SharedModule from 'app/shared/shared.module';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';

import { IModePaiement } from '../mode-paiement.model';
import { ModePaiementService } from '../service/mode-paiement.service';
import { ModePaiementFormGroup, ModePaiementFormService } from './mode-paiement-form.service';

@Component({
  selector: 'jhi-mode-paiement-update',
  templateUrl: './mode-paiement-update.component.html',
  imports: [SharedModule, FormsModule, ReactiveFormsModule],
})
export class ModePaiementUpdateComponent implements OnInit {
  isSaving = false;
  modePaiement: IModePaiement | null = null;

  protected modePaiementService = inject(ModePaiementService);
  protected modePaiementFormService = inject(ModePaiementFormService);
  protected activatedRoute = inject(ActivatedRoute);

  // eslint-disable-next-line @typescript-eslint/member-ordering
  editForm: ModePaiementFormGroup = this.modePaiementFormService.createModePaiementFormGroup();

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ modePaiement }) => {
      this.modePaiement = modePaiement;
      if (modePaiement) {
        this.updateForm(modePaiement);
      }
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const modePaiement = this.modePaiementFormService.getModePaiement(this.editForm);
    if (modePaiement.id !== null) {
      this.subscribeToSaveResponse(this.modePaiementService.update(modePaiement));
    } else {
      this.subscribeToSaveResponse(this.modePaiementService.create(modePaiement));
    }
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IModePaiement>>): void {
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

  protected updateForm(modePaiement: IModePaiement): void {
    this.modePaiement = modePaiement;
    this.modePaiementFormService.resetForm(this.editForm, modePaiement);
  }
}
