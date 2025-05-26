import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpResponse, provideHttpClient } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Subject, from, of } from 'rxjs';

import { CommissionServiceService } from '../service/commission-service.service';
import { ICommissionService } from '../commission-service.model';
import { CommissionServiceFormService } from './commission-service-form.service';

import { CommissionServiceUpdateComponent } from './commission-service-update.component';

describe('CommissionService Management Update Component', () => {
  let comp: CommissionServiceUpdateComponent;
  let fixture: ComponentFixture<CommissionServiceUpdateComponent>;
  let activatedRoute: ActivatedRoute;
  let commissionServiceFormService: CommissionServiceFormService;
  let commissionServiceService: CommissionServiceService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [CommissionServiceUpdateComponent],
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
      .overrideTemplate(CommissionServiceUpdateComponent, '')
      .compileComponents();

    fixture = TestBed.createComponent(CommissionServiceUpdateComponent);
    activatedRoute = TestBed.inject(ActivatedRoute);
    commissionServiceFormService = TestBed.inject(CommissionServiceFormService);
    commissionServiceService = TestBed.inject(CommissionServiceService);

    comp = fixture.componentInstance;
  });

  describe('ngOnInit', () => {
    it('should update editForm', () => {
      const commissionService: ICommissionService = { id: 16010 };

      activatedRoute.data = of({ commissionService });
      comp.ngOnInit();

      expect(comp.commissionService).toEqual(commissionService);
    });
  });

  describe('save', () => {
    it('should call update service on save for existing entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<ICommissionService>>();
      const commissionService = { id: 19824 };
      jest.spyOn(commissionServiceFormService, 'getCommissionService').mockReturnValue(commissionService);
      jest.spyOn(commissionServiceService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ commissionService });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: commissionService }));
      saveSubject.complete();

      // THEN
      expect(commissionServiceFormService.getCommissionService).toHaveBeenCalled();
      expect(comp.previousState).toHaveBeenCalled();
      expect(commissionServiceService.update).toHaveBeenCalledWith(expect.objectContaining(commissionService));
      expect(comp.isSaving).toEqual(false);
    });

    it('should call create service on save for new entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<ICommissionService>>();
      const commissionService = { id: 19824 };
      jest.spyOn(commissionServiceFormService, 'getCommissionService').mockReturnValue({ id: null });
      jest.spyOn(commissionServiceService, 'create').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ commissionService: null });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: commissionService }));
      saveSubject.complete();

      // THEN
      expect(commissionServiceFormService.getCommissionService).toHaveBeenCalled();
      expect(commissionServiceService.create).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).toHaveBeenCalled();
    });

    it('should set isSaving to false on error', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<ICommissionService>>();
      const commissionService = { id: 19824 };
      jest.spyOn(commissionServiceService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ commissionService });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.error('This is an error!');

      // THEN
      expect(commissionServiceService.update).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).not.toHaveBeenCalled();
    });
  });
});
