import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpResponse, provideHttpClient } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Subject, from, of } from 'rxjs';

import { PartieDeJourneeService } from '../service/partie-de-journee.service';
import { IPartieDeJournee } from '../partie-de-journee.model';
import { PartieDeJourneeFormService } from './partie-de-journee-form.service';

import { PartieDeJourneeUpdateComponent } from './partie-de-journee-update.component';

describe('PartieDeJournee Management Update Component', () => {
  let comp: PartieDeJourneeUpdateComponent;
  let fixture: ComponentFixture<PartieDeJourneeUpdateComponent>;
  let activatedRoute: ActivatedRoute;
  let partieDeJourneeFormService: PartieDeJourneeFormService;
  let partieDeJourneeService: PartieDeJourneeService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [PartieDeJourneeUpdateComponent],
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
      .overrideTemplate(PartieDeJourneeUpdateComponent, '')
      .compileComponents();

    fixture = TestBed.createComponent(PartieDeJourneeUpdateComponent);
    activatedRoute = TestBed.inject(ActivatedRoute);
    partieDeJourneeFormService = TestBed.inject(PartieDeJourneeFormService);
    partieDeJourneeService = TestBed.inject(PartieDeJourneeService);

    comp = fixture.componentInstance;
  });

  describe('ngOnInit', () => {
    it('should update editForm', () => {
      const partieDeJournee: IPartieDeJournee = { id: 25600 };

      activatedRoute.data = of({ partieDeJournee });
      comp.ngOnInit();

      expect(comp.partieDeJournee).toEqual(partieDeJournee);
    });
  });

  describe('save', () => {
    it('should call update service on save for existing entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IPartieDeJournee>>();
      const partieDeJournee = { id: 24117 };
      jest.spyOn(partieDeJourneeFormService, 'getPartieDeJournee').mockReturnValue(partieDeJournee);
      jest.spyOn(partieDeJourneeService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ partieDeJournee });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: partieDeJournee }));
      saveSubject.complete();

      // THEN
      expect(partieDeJourneeFormService.getPartieDeJournee).toHaveBeenCalled();
      expect(comp.previousState).toHaveBeenCalled();
      expect(partieDeJourneeService.update).toHaveBeenCalledWith(expect.objectContaining(partieDeJournee));
      expect(comp.isSaving).toEqual(false);
    });

    it('should call create service on save for new entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IPartieDeJournee>>();
      const partieDeJournee = { id: 24117 };
      jest.spyOn(partieDeJourneeFormService, 'getPartieDeJournee').mockReturnValue({ id: null });
      jest.spyOn(partieDeJourneeService, 'create').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ partieDeJournee: null });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: partieDeJournee }));
      saveSubject.complete();

      // THEN
      expect(partieDeJourneeFormService.getPartieDeJournee).toHaveBeenCalled();
      expect(partieDeJourneeService.create).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).toHaveBeenCalled();
    });

    it('should set isSaving to false on error', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IPartieDeJournee>>();
      const partieDeJournee = { id: 24117 };
      jest.spyOn(partieDeJourneeService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ partieDeJournee });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.error('This is an error!');

      // THEN
      expect(partieDeJourneeService.update).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).not.toHaveBeenCalled();
    });
  });
});
