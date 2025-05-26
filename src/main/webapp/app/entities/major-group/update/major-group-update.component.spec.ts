import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpResponse, provideHttpClient } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Subject, from, of } from 'rxjs';

import { MajorGroupService } from '../service/major-group.service';
import { IMajorGroup } from '../major-group.model';
import { MajorGroupFormService } from './major-group-form.service';

import { MajorGroupUpdateComponent } from './major-group-update.component';

describe('MajorGroup Management Update Component', () => {
  let comp: MajorGroupUpdateComponent;
  let fixture: ComponentFixture<MajorGroupUpdateComponent>;
  let activatedRoute: ActivatedRoute;
  let majorGroupFormService: MajorGroupFormService;
  let majorGroupService: MajorGroupService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [MajorGroupUpdateComponent],
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
      .overrideTemplate(MajorGroupUpdateComponent, '')
      .compileComponents();

    fixture = TestBed.createComponent(MajorGroupUpdateComponent);
    activatedRoute = TestBed.inject(ActivatedRoute);
    majorGroupFormService = TestBed.inject(MajorGroupFormService);
    majorGroupService = TestBed.inject(MajorGroupService);

    comp = fixture.componentInstance;
  });

  describe('ngOnInit', () => {
    it('should update editForm', () => {
      const majorGroup: IMajorGroup = { id: 14386 };

      activatedRoute.data = of({ majorGroup });
      comp.ngOnInit();

      expect(comp.majorGroup).toEqual(majorGroup);
    });
  });

  describe('save', () => {
    it('should call update service on save for existing entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IMajorGroup>>();
      const majorGroup = { id: 5994 };
      jest.spyOn(majorGroupFormService, 'getMajorGroup').mockReturnValue(majorGroup);
      jest.spyOn(majorGroupService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ majorGroup });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: majorGroup }));
      saveSubject.complete();

      // THEN
      expect(majorGroupFormService.getMajorGroup).toHaveBeenCalled();
      expect(comp.previousState).toHaveBeenCalled();
      expect(majorGroupService.update).toHaveBeenCalledWith(expect.objectContaining(majorGroup));
      expect(comp.isSaving).toEqual(false);
    });

    it('should call create service on save for new entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IMajorGroup>>();
      const majorGroup = { id: 5994 };
      jest.spyOn(majorGroupFormService, 'getMajorGroup').mockReturnValue({ id: null });
      jest.spyOn(majorGroupService, 'create').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ majorGroup: null });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: majorGroup }));
      saveSubject.complete();

      // THEN
      expect(majorGroupFormService.getMajorGroup).toHaveBeenCalled();
      expect(majorGroupService.create).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).toHaveBeenCalled();
    });

    it('should set isSaving to false on error', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IMajorGroup>>();
      const majorGroup = { id: 5994 };
      jest.spyOn(majorGroupService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ majorGroup });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.error('This is an error!');

      // THEN
      expect(majorGroupService.update).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).not.toHaveBeenCalled();
    });
  });
});
