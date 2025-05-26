import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpResponse, provideHttpClient } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Subject, from, of } from 'rxjs';

import { FactureDetailService } from '../service/facture-detail.service';
import { IFactureDetail } from '../facture-detail.model';
import { FactureDetailFormService } from './facture-detail-form.service';

import { FactureDetailUpdateComponent } from './facture-detail-update.component';

describe('FactureDetail Management Update Component', () => {
  let comp: FactureDetailUpdateComponent;
  let fixture: ComponentFixture<FactureDetailUpdateComponent>;
  let activatedRoute: ActivatedRoute;
  let factureDetailFormService: FactureDetailFormService;
  let factureDetailService: FactureDetailService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [FactureDetailUpdateComponent],
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
      .overrideTemplate(FactureDetailUpdateComponent, '')
      .compileComponents();

    fixture = TestBed.createComponent(FactureDetailUpdateComponent);
    activatedRoute = TestBed.inject(ActivatedRoute);
    factureDetailFormService = TestBed.inject(FactureDetailFormService);
    factureDetailService = TestBed.inject(FactureDetailService);

    comp = fixture.componentInstance;
  });

  describe('ngOnInit', () => {
    it('should update editForm', () => {
      const factureDetail: IFactureDetail = { id: 13339 };

      activatedRoute.data = of({ factureDetail });
      comp.ngOnInit();

      expect(comp.factureDetail).toEqual(factureDetail);
    });
  });

  describe('save', () => {
    it('should call update service on save for existing entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IFactureDetail>>();
      const factureDetail = { id: 27600 };
      jest.spyOn(factureDetailFormService, 'getFactureDetail').mockReturnValue(factureDetail);
      jest.spyOn(factureDetailService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ factureDetail });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: factureDetail }));
      saveSubject.complete();

      // THEN
      expect(factureDetailFormService.getFactureDetail).toHaveBeenCalled();
      expect(comp.previousState).toHaveBeenCalled();
      expect(factureDetailService.update).toHaveBeenCalledWith(expect.objectContaining(factureDetail));
      expect(comp.isSaving).toEqual(false);
    });

    it('should call create service on save for new entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IFactureDetail>>();
      const factureDetail = { id: 27600 };
      jest.spyOn(factureDetailFormService, 'getFactureDetail').mockReturnValue({ id: null });
      jest.spyOn(factureDetailService, 'create').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ factureDetail: null });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: factureDetail }));
      saveSubject.complete();

      // THEN
      expect(factureDetailFormService.getFactureDetail).toHaveBeenCalled();
      expect(factureDetailService.create).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).toHaveBeenCalled();
    });

    it('should set isSaving to false on error', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IFactureDetail>>();
      const factureDetail = { id: 27600 };
      jest.spyOn(factureDetailService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ factureDetail });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.error('This is an error!');

      // THEN
      expect(factureDetailService.update).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).not.toHaveBeenCalled();
    });
  });
});
