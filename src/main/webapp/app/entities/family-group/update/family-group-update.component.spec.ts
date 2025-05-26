import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpResponse, provideHttpClient } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Subject, from, of } from 'rxjs';

import { FamilyGroupService } from '../service/family-group.service';
import { IFamilyGroup } from '../family-group.model';
import { FamilyGroupFormService } from './family-group-form.service';

import { FamilyGroupUpdateComponent } from './family-group-update.component';

describe('FamilyGroup Management Update Component', () => {
  let comp: FamilyGroupUpdateComponent;
  let fixture: ComponentFixture<FamilyGroupUpdateComponent>;
  let activatedRoute: ActivatedRoute;
  let familyGroupFormService: FamilyGroupFormService;
  let familyGroupService: FamilyGroupService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [FamilyGroupUpdateComponent],
      providers: [
        provideHttpClient(),
        FormBuilder,
        {
          provide: ActivatedRoute,
          useValue: {
            params: from([{}]),
          },
        },
      ],
    })
      .overrideTemplate(FamilyGroupUpdateComponent, '')
      .compileComponents();

    fixture = TestBed.createComponent(FamilyGroupUpdateComponent);
    activatedRoute = TestBed.inject(ActivatedRoute);
    familyGroupFormService = TestBed.inject(FamilyGroupFormService);
    familyGroupService = TestBed.inject(FamilyGroupService);

    comp = fixture.componentInstance;
  });

  describe('ngOnInit', () => {
    it('should update editForm', () => {
      const familyGroup: IFamilyGroup = { id: 19289 };

      activatedRoute.data = of({ familyGroup });
      comp.ngOnInit();

      expect(comp.familyGroup).toEqual(familyGroup);
    });
  });

  describe('save', () => {
    it('should call update service on save for existing entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IFamilyGroup>>();
      const familyGroup = { id: 28164 };
      jest.spyOn(familyGroupFormService, 'getFamilyGroup').mockReturnValue(familyGroup);
      jest.spyOn(familyGroupService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ familyGroup });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: familyGroup }));
      saveSubject.complete();

      // THEN
      expect(familyGroupFormService.getFamilyGroup).toHaveBeenCalled();
      expect(comp.previousState).toHaveBeenCalled();
      expect(familyGroupService.update).toHaveBeenCalledWith(expect.objectContaining(familyGroup));
      expect(comp.isSaving).toEqual(false);
    });

    it('should call create service on save for new entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IFamilyGroup>>();
      const familyGroup = { id: 28164 };
      jest.spyOn(familyGroupFormService, 'getFamilyGroup').mockReturnValue({ id: null });
      jest.spyOn(familyGroupService, 'create').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ familyGroup: null });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: familyGroup }));
      saveSubject.complete();

      // THEN
      expect(familyGroupFormService.getFamilyGroup).toHaveBeenCalled();
      expect(familyGroupService.create).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).toHaveBeenCalled();
    });

    it('should set isSaving to false on error', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IFamilyGroup>>();
      const familyGroup = { id: 28164 };
      jest.spyOn(familyGroupService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ familyGroup });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.error('This is an error!');

      // THEN
      expect(familyGroupService.update).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).not.toHaveBeenCalled();
    });
  });
});
