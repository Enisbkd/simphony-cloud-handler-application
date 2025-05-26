import { Component, OnInit, inject } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize } from 'rxjs/operators';

import SharedModule from 'app/shared/shared.module';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';

import { ICodeRaison } from '../code-raison.model';
import { CodeRaisonService } from '../service/code-raison.service';
import { CodeRaisonFormGroup, CodeRaisonFormService } from './code-raison-form.service';

@Component({
  selector: 'jhi-code-raison-update',
  templateUrl: './code-raison-update.component.html',
  imports: [SharedModule, FormsModule, ReactiveFormsModule],
})
export class CodeRaisonUpdateComponent implements OnInit {
  isSaving = false;
  codeRaison: ICodeRaison | null = null;

  protected codeRaisonService = inject(CodeRaisonService);
  protected codeRaisonFormService = inject(CodeRaisonFormService);
  protected activatedRoute = inject(ActivatedRoute);

  // eslint-disable-next-line @typescript-eslint/member-ordering
  editForm: CodeRaisonFormGroup = this.codeRaisonFormService.createCodeRaisonFormGroup();

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ codeRaison }) => {
      this.codeRaison = codeRaison;
      if (codeRaison) {
        this.updateForm(codeRaison);
      }
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const codeRaison = this.codeRaisonFormService.getCodeRaison(this.editForm);
    if (codeRaison.id !== null) {
      this.subscribeToSaveResponse(this.codeRaisonService.update(codeRaison));
    } else {
      this.subscribeToSaveResponse(this.codeRaisonService.create(codeRaison));
    }
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<ICodeRaison>>): void {
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

  protected updateForm(codeRaison: ICodeRaison): void {
    this.codeRaison = codeRaison;
    this.codeRaisonFormService.resetForm(this.editForm, codeRaison);
  }
}
