import { Component, OnInit, inject } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize } from 'rxjs/operators';

import SharedModule from 'app/shared/shared.module';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';

import { IEmploye } from '../employe.model';
import { EmployeService } from '../service/employe.service';
import { EmployeFormGroup, EmployeFormService } from './employe-form.service';

@Component({
  selector: 'jhi-employe-update',
  templateUrl: './employe-update.component.html',
  imports: [SharedModule, FormsModule, ReactiveFormsModule],
})
export class EmployeUpdateComponent implements OnInit {
  isSaving = false;
  employe: IEmploye | null = null;

  protected employeService = inject(EmployeService);
  protected employeFormService = inject(EmployeFormService);
  protected activatedRoute = inject(ActivatedRoute);

  // eslint-disable-next-line @typescript-eslint/member-ordering
  editForm: EmployeFormGroup = this.employeFormService.createEmployeFormGroup();

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ employe }) => {
      this.employe = employe;
      if (employe) {
        this.updateForm(employe);
      }
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const employe = this.employeFormService.getEmploye(this.editForm);
    if (employe.id !== null) {
      this.subscribeToSaveResponse(this.employeService.update(employe));
    } else {
      this.subscribeToSaveResponse(this.employeService.create(employe));
    }
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IEmploye>>): void {
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

  protected updateForm(employe: IEmploye): void {
    this.employe = employe;
    this.employeFormService.resetForm(this.editForm, employe);
  }
}
