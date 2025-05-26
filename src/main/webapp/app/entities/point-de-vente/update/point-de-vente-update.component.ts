import { Component, OnInit, inject } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize } from 'rxjs/operators';

import SharedModule from 'app/shared/shared.module';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';

import { IPointDeVente } from '../point-de-vente.model';
import { PointDeVenteService } from '../service/point-de-vente.service';
import { PointDeVenteFormGroup, PointDeVenteFormService } from './point-de-vente-form.service';

@Component({
  selector: 'jhi-point-de-vente-update',
  templateUrl: './point-de-vente-update.component.html',
  imports: [SharedModule, FormsModule, ReactiveFormsModule],
})
export class PointDeVenteUpdateComponent implements OnInit {
  isSaving = false;
  pointDeVente: IPointDeVente | null = null;

  protected pointDeVenteService = inject(PointDeVenteService);
  protected pointDeVenteFormService = inject(PointDeVenteFormService);
  protected activatedRoute = inject(ActivatedRoute);

  // eslint-disable-next-line @typescript-eslint/member-ordering
  editForm: PointDeVenteFormGroup = this.pointDeVenteFormService.createPointDeVenteFormGroup();

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ pointDeVente }) => {
      this.pointDeVente = pointDeVente;
      if (pointDeVente) {
        this.updateForm(pointDeVente);
      }
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const pointDeVente = this.pointDeVenteFormService.getPointDeVente(this.editForm);
    if (pointDeVente.id !== null) {
      this.subscribeToSaveResponse(this.pointDeVenteService.update(pointDeVente));
    } else {
      this.subscribeToSaveResponse(this.pointDeVenteService.create(pointDeVente));
    }
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IPointDeVente>>): void {
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

  protected updateForm(pointDeVente: IPointDeVente): void {
    this.pointDeVente = pointDeVente;
    this.pointDeVenteFormService.resetForm(this.editForm, pointDeVente);
  }
}
