import { Component, OnInit, inject } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize } from 'rxjs/operators';

import SharedModule from 'app/shared/shared.module';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';

import { IFactureEnTete } from '../facture-en-tete.model';
import { FactureEnTeteService } from '../service/facture-en-tete.service';
import { FactureEnTeteFormGroup, FactureEnTeteFormService } from './facture-en-tete-form.service';

@Component({
  selector: 'jhi-facture-en-tete-update',
  templateUrl: './facture-en-tete-update.component.html',
  imports: [SharedModule, FormsModule, ReactiveFormsModule],
})
export class FactureEnTeteUpdateComponent implements OnInit {
  isSaving = false;
  factureEnTete: IFactureEnTete | null = null;

  protected factureEnTeteService = inject(FactureEnTeteService);
  protected factureEnTeteFormService = inject(FactureEnTeteFormService);
  protected activatedRoute = inject(ActivatedRoute);

  // eslint-disable-next-line @typescript-eslint/member-ordering
  editForm: FactureEnTeteFormGroup = this.factureEnTeteFormService.createFactureEnTeteFormGroup();

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ factureEnTete }) => {
      this.factureEnTete = factureEnTete;
      if (factureEnTete) {
        this.updateForm(factureEnTete);
      }
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const factureEnTete = this.factureEnTeteFormService.getFactureEnTete(this.editForm);
    if (factureEnTete.id !== null) {
      this.subscribeToSaveResponse(this.factureEnTeteService.update(factureEnTete));
    } else {
      this.subscribeToSaveResponse(this.factureEnTeteService.create(factureEnTete));
    }
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IFactureEnTete>>): void {
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

  protected updateForm(factureEnTete: IFactureEnTete): void {
    this.factureEnTete = factureEnTete;
    this.factureEnTeteFormService.resetForm(this.editForm, factureEnTete);
  }
}
