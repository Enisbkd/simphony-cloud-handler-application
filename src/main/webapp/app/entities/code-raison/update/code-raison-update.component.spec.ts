import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpResponse, provideHttpClient } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Subject, from, of } from 'rxjs';

import { CodeRaisonService } from '../service/code-raison.service';
import { ICodeRaison } from '../code-raison.model';
import { CodeRaisonFormService } from './code-raison-form.service';

import { CodeRaisonUpdateComponent } from './code-raison-update.component';

describe('CodeRaison Management Update Component', () => {
  let comp: CodeRaisonUpdateComponent;
  let fixture: ComponentFixture<CodeRaisonUpdateComponent>;
  let activatedRoute: ActivatedRoute;
  let codeRaisonFormService: CodeRaisonFormService;
  let codeRaisonService: CodeRaisonService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [CodeRaisonUpdateComponent],
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
      .overrideTemplate(CodeRaisonUpdateComponent, '')
      .compileComponents();

    fixture = TestBed.createComponent(CodeRaisonUpdateComponent);
    activatedRoute = TestBed.inject(ActivatedRoute);
    codeRaisonFormService = TestBed.inject(CodeRaisonFormService);
    codeRaisonService = TestBed.inject(CodeRaisonService);

    comp = fixture.componentInstance;
  });

  describe('ngOnInit', () => {
    it('should update editForm', () => {
      const codeRaison: ICodeRaison = { id: 30195 };

      activatedRoute.data = of({ codeRaison });
      comp.ngOnInit();

      expect(comp.codeRaison).toEqual(codeRaison);
    });
  });

  describe('save', () => {
    it('should call update service on save for existing entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<ICodeRaison>>();
      const codeRaison = { id: 29266 };
      jest.spyOn(codeRaisonFormService, 'getCodeRaison').mockReturnValue(codeRaison);
      jest.spyOn(codeRaisonService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ codeRaison });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: codeRaison }));
      saveSubject.complete();

      // THEN
      expect(codeRaisonFormService.getCodeRaison).toHaveBeenCalled();
      expect(comp.previousState).toHaveBeenCalled();
      expect(codeRaisonService.update).toHaveBeenCalledWith(expect.objectContaining(codeRaison));
      expect(comp.isSaving).toEqual(false);
    });

    it('should call create service on save for new entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<ICodeRaison>>();
      const codeRaison = { id: 29266 };
      jest.spyOn(codeRaisonFormService, 'getCodeRaison').mockReturnValue({ id: null });
      jest.spyOn(codeRaisonService, 'create').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ codeRaison: null });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: codeRaison }));
      saveSubject.complete();

      // THEN
      expect(codeRaisonFormService.getCodeRaison).toHaveBeenCalled();
      expect(codeRaisonService.create).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).toHaveBeenCalled();
    });

    it('should set isSaving to false on error', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<ICodeRaison>>();
      const codeRaison = { id: 29266 };
      jest.spyOn(codeRaisonService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ codeRaison });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.error('This is an error!');

      // THEN
      expect(codeRaisonService.update).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).not.toHaveBeenCalled();
    });
  });
});
