import { Component, OnInit, inject } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize } from 'rxjs/operators';

import SharedModule from 'app/shared/shared.module';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';

import { IFamilyGroup } from '../family-group.model';
import { FamilyGroupService } from '../service/family-group.service';
import { FamilyGroupFormGroup, FamilyGroupFormService } from './family-group-form.service';

@Component({
  selector: 'jhi-family-group-update',
  templateUrl: './family-group-update.component.html',
  imports: [SharedModule, FormsModule, ReactiveFormsModule],
})
export class FamilyGroupUpdateComponent implements OnInit {
  isSaving = false;
  familyGroup: IFamilyGroup | null = null;

  protected familyGroupService = inject(FamilyGroupService);
  protected familyGroupFormService = inject(FamilyGroupFormService);
  protected activatedRoute = inject(ActivatedRoute);

  // eslint-disable-next-line @typescript-eslint/member-ordering
  editForm: FamilyGroupFormGroup = this.familyGroupFormService.createFamilyGroupFormGroup();

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ familyGroup }) => {
      this.familyGroup = familyGroup;
      if (familyGroup) {
        this.updateForm(familyGroup);
      }
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const familyGroup = this.familyGroupFormService.getFamilyGroup(this.editForm);
    if (familyGroup.id !== null) {
      this.subscribeToSaveResponse(this.familyGroupService.update(familyGroup));
    } else {
      this.subscribeToSaveResponse(this.familyGroupService.create(familyGroup));
    }
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IFamilyGroup>>): void {
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

  protected updateForm(familyGroup: IFamilyGroup): void {
    this.familyGroup = familyGroup;
    this.familyGroupFormService.resetForm(this.editForm, familyGroup);
  }
}
