import { Component, OnInit, inject } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize } from 'rxjs/operators';

import SharedModule from 'app/shared/shared.module';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';

import { IMajorGroup } from '../major-group.model';
import { MajorGroupService } from '../service/major-group.service';
import { MajorGroupFormGroup, MajorGroupFormService } from './major-group-form.service';

@Component({
  selector: 'jhi-major-group-update',
  templateUrl: './major-group-update.component.html',
  imports: [SharedModule, FormsModule, ReactiveFormsModule],
})
export class MajorGroupUpdateComponent implements OnInit {
  isSaving = false;
  majorGroup: IMajorGroup | null = null;

  protected majorGroupService = inject(MajorGroupService);
  protected majorGroupFormService = inject(MajorGroupFormService);
  protected activatedRoute = inject(ActivatedRoute);

  // eslint-disable-next-line @typescript-eslint/member-ordering
  editForm: MajorGroupFormGroup = this.majorGroupFormService.createMajorGroupFormGroup();

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ majorGroup }) => {
      this.majorGroup = majorGroup;
      if (majorGroup) {
        this.updateForm(majorGroup);
      }
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const majorGroup = this.majorGroupFormService.getMajorGroup(this.editForm);
    if (majorGroup.id !== null) {
      this.subscribeToSaveResponse(this.majorGroupService.update(majorGroup));
    } else {
      this.subscribeToSaveResponse(this.majorGroupService.create(majorGroup));
    }
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IMajorGroup>>): void {
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

  protected updateForm(majorGroup: IMajorGroup): void {
    this.majorGroup = majorGroup;
    this.majorGroupFormService.resetForm(this.editForm, majorGroup);
  }
}
