import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpResponse, provideHttpClient } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Subject, from, of } from 'rxjs';

import { TaxeService } from '../service/taxe.service';
import { ITaxe } from '../taxe.model';
import { TaxeFormService } from './taxe-form.service';

import { TaxeUpdateComponent } from './taxe-update.component';

describe('Taxe Management Update Component', () => {
  let comp: TaxeUpdateComponent;
  let fixture: ComponentFixture<TaxeUpdateComponent>;
  let activatedRoute: ActivatedRoute;
  let taxeFormService: TaxeFormService;
  let taxeService: TaxeService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [TaxeUpdateComponent],
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
      .overrideTemplate(TaxeUpdateComponent, '')
      .compileComponents();

    fixture = TestBed.createComponent(TaxeUpdateComponent);
    activatedRoute = TestBed.inject(ActivatedRoute);
    taxeFormService = TestBed.inject(TaxeFormService);
    taxeService = TestBed.inject(TaxeService);

    comp = fixture.componentInstance;
  });

  describe('ngOnInit', () => {
    it('should update editForm', () => {
      const taxe: ITaxe = { id: 32741 };

      activatedRoute.data = of({ taxe });
      comp.ngOnInit();

      expect(comp.taxe).toEqual(taxe);
    });
  });

  describe('save', () => {
    it('should call update service on save for existing entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<ITaxe>>();
      const taxe = { id: 11733 };
      jest.spyOn(taxeFormService, 'getTaxe').mockReturnValue(taxe);
      jest.spyOn(taxeService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ taxe });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: taxe }));
      saveSubject.complete();

      // THEN
      expect(taxeFormService.getTaxe).toHaveBeenCalled();
      expect(comp.previousState).toHaveBeenCalled();
      expect(taxeService.update).toHaveBeenCalledWith(expect.objectContaining(taxe));
      expect(comp.isSaving).toEqual(false);
    });

    it('should call create service on save for new entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<ITaxe>>();
      const taxe = { id: 11733 };
      jest.spyOn(taxeFormService, 'getTaxe').mockReturnValue({ id: null });
      jest.spyOn(taxeService, 'create').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ taxe: null });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: taxe }));
      saveSubject.complete();

      // THEN
      expect(taxeFormService.getTaxe).toHaveBeenCalled();
      expect(taxeService.create).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).toHaveBeenCalled();
    });

    it('should set isSaving to false on error', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<ITaxe>>();
      const taxe = { id: 11733 };
      jest.spyOn(taxeService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ taxe });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.error('This is an error!');

      // THEN
      expect(taxeService.update).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).not.toHaveBeenCalled();
    });
  });
});
