import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpResponse, provideHttpClient } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Subject, from, of } from 'rxjs';

import { EmployeService } from '../service/employe.service';
import { IEmploye } from '../employe.model';
import { EmployeFormService } from './employe-form.service';

import { EmployeUpdateComponent } from './employe-update.component';

describe('Employe Management Update Component', () => {
  let comp: EmployeUpdateComponent;
  let fixture: ComponentFixture<EmployeUpdateComponent>;
  let activatedRoute: ActivatedRoute;
  let employeFormService: EmployeFormService;
  let employeService: EmployeService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [EmployeUpdateComponent],
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
      .overrideTemplate(EmployeUpdateComponent, '')
      .compileComponents();

    fixture = TestBed.createComponent(EmployeUpdateComponent);
    activatedRoute = TestBed.inject(ActivatedRoute);
    employeFormService = TestBed.inject(EmployeFormService);
    employeService = TestBed.inject(EmployeService);

    comp = fixture.componentInstance;
  });

  describe('ngOnInit', () => {
    it('should update editForm', () => {
      const employe: IEmploye = { id: 22213 };

      activatedRoute.data = of({ employe });
      comp.ngOnInit();

      expect(comp.employe).toEqual(employe);
    });
  });

  describe('save', () => {
    it('should call update service on save for existing entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IEmploye>>();
      const employe = { id: 10360 };
      jest.spyOn(employeFormService, 'getEmploye').mockReturnValue(employe);
      jest.spyOn(employeService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ employe });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: employe }));
      saveSubject.complete();

      // THEN
      expect(employeFormService.getEmploye).toHaveBeenCalled();
      expect(comp.previousState).toHaveBeenCalled();
      expect(employeService.update).toHaveBeenCalledWith(expect.objectContaining(employe));
      expect(comp.isSaving).toEqual(false);
    });

    it('should call create service on save for new entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IEmploye>>();
      const employe = { id: 10360 };
      jest.spyOn(employeFormService, 'getEmploye').mockReturnValue({ id: null });
      jest.spyOn(employeService, 'create').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ employe: null });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: employe }));
      saveSubject.complete();

      // THEN
      expect(employeFormService.getEmploye).toHaveBeenCalled();
      expect(employeService.create).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).toHaveBeenCalled();
    });

    it('should set isSaving to false on error', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IEmploye>>();
      const employe = { id: 10360 };
      jest.spyOn(employeService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ employe });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.error('This is an error!');

      // THEN
      expect(employeService.update).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).not.toHaveBeenCalled();
    });
  });
});
