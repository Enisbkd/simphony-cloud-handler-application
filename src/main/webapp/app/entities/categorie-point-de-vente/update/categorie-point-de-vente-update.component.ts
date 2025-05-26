import { Component, OnInit, inject } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize } from 'rxjs/operators';

import SharedModule from 'app/shared/shared.module';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';

import { ICategoriePointDeVente } from '../categorie-point-de-vente.model';
import { CategoriePointDeVenteService } from '../service/categorie-point-de-vente.service';
import { CategoriePointDeVenteFormGroup, CategoriePointDeVenteFormService } from './categorie-point-de-vente-form.service';

@Component({
  selector: 'jhi-categorie-point-de-vente-update',
  templateUrl: './categorie-point-de-vente-update.component.html',
  imports: [SharedModule, FormsModule, ReactiveFormsModule],
})
export class CategoriePointDeVenteUpdateComponent implements OnInit {
  isSaving = false;
  categoriePointDeVente: ICategoriePointDeVente | null = null;

  protected categoriePointDeVenteService = inject(CategoriePointDeVenteService);
  protected categoriePointDeVenteFormService = inject(CategoriePointDeVenteFormService);
  protected activatedRoute = inject(ActivatedRoute);

  // eslint-disable-next-line @typescript-eslint/member-ordering
  editForm: CategoriePointDeVenteFormGroup = this.categoriePointDeVenteFormService.createCategoriePointDeVenteFormGroup();

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ categoriePointDeVente }) => {
      this.categoriePointDeVente = categoriePointDeVente;
      if (categoriePointDeVente) {
        this.updateForm(categoriePointDeVente);
      }
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const categoriePointDeVente = this.categoriePointDeVenteFormService.getCategoriePointDeVente(this.editForm);
    if (categoriePointDeVente.id !== null) {
      this.subscribeToSaveResponse(this.categoriePointDeVenteService.update(categoriePointDeVente));
    } else {
      this.subscribeToSaveResponse(this.categoriePointDeVenteService.create(categoriePointDeVente));
    }
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<ICategoriePointDeVente>>): void {
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

  protected updateForm(categoriePointDeVente: ICategoriePointDeVente): void {
    this.categoriePointDeVente = categoriePointDeVente;
    this.categoriePointDeVenteFormService.resetForm(this.editForm, categoriePointDeVente);
  }
}
