import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpResponse, provideHttpClient } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Subject, from, of } from 'rxjs';

import { HierService } from '../service/hier.service';
import { IHier } from '../hier.model';
import { HierFormService } from './hier-form.service';

import { HierUpdateComponent } from './hier-update.component';

describe('Hier Management Update Component', () => {
  let comp: HierUpdateComponent;
  let fixture: ComponentFixture<HierUpdateComponent>;
  let activatedRoute: ActivatedRoute;
  let hierFormService: HierFormService;
  let hierService: HierService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HierUpdateComponent],
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
      .overrideTemplate(HierUpdateComponent, '')
      .compileComponents();

    fixture = TestBed.createComponent(HierUpdateComponent);
    activatedRoute = TestBed.inject(ActivatedRoute);
    hierFormService = TestBed.inject(HierFormService);
    hierService = TestBed.inject(HierService);

    comp = fixture.componentInstance;
  });

  describe('ngOnInit', () => {
    it('should update editForm', () => {
      const hier: IHier = { id: '099de192-b562-45ef-9f3e-f62533c56391' };

      activatedRoute.data = of({ hier });
      comp.ngOnInit();

      expect(comp.hier).toEqual(hier);
    });
  });

  describe('save', () => {
    it('should call update service on save for existing entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IHier>>();
      const hier = { id: '5b7fea44-24f3-4956-abaa-0999154e9c91' };
      jest.spyOn(hierFormService, 'getHier').mockReturnValue(hier);
      jest.spyOn(hierService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ hier });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: hier }));
      saveSubject.complete();

      // THEN
      expect(hierFormService.getHier).toHaveBeenCalled();
      expect(comp.previousState).toHaveBeenCalled();
      expect(hierService.update).toHaveBeenCalledWith(expect.objectContaining(hier));
      expect(comp.isSaving).toEqual(false);
    });

    it('should call create service on save for new entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IHier>>();
      const hier = { id: '5b7fea44-24f3-4956-abaa-0999154e9c91' };
      jest.spyOn(hierFormService, 'getHier').mockReturnValue({ id: null });
      jest.spyOn(hierService, 'create').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ hier: null });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: hier }));
      saveSubject.complete();

      // THEN
      expect(hierFormService.getHier).toHaveBeenCalled();
      expect(hierService.create).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).toHaveBeenCalled();
    });

    it('should set isSaving to false on error', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IHier>>();
      const hier = { id: '5b7fea44-24f3-4956-abaa-0999154e9c91' };
      jest.spyOn(hierService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ hier });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.error('This is an error!');

      // THEN
      expect(hierService.update).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).not.toHaveBeenCalled();
    });
  });
});
