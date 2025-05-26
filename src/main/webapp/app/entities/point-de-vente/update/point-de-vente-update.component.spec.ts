import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpResponse, provideHttpClient } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Subject, from, of } from 'rxjs';

import { PointDeVenteService } from '../service/point-de-vente.service';
import { IPointDeVente } from '../point-de-vente.model';
import { PointDeVenteFormService } from './point-de-vente-form.service';

import { PointDeVenteUpdateComponent } from './point-de-vente-update.component';

describe('PointDeVente Management Update Component', () => {
  let comp: PointDeVenteUpdateComponent;
  let fixture: ComponentFixture<PointDeVenteUpdateComponent>;
  let activatedRoute: ActivatedRoute;
  let pointDeVenteFormService: PointDeVenteFormService;
  let pointDeVenteService: PointDeVenteService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [PointDeVenteUpdateComponent],
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
      .overrideTemplate(PointDeVenteUpdateComponent, '')
      .compileComponents();

    fixture = TestBed.createComponent(PointDeVenteUpdateComponent);
    activatedRoute = TestBed.inject(ActivatedRoute);
    pointDeVenteFormService = TestBed.inject(PointDeVenteFormService);
    pointDeVenteService = TestBed.inject(PointDeVenteService);

    comp = fixture.componentInstance;
  });

  describe('ngOnInit', () => {
    it('should update editForm', () => {
      const pointDeVente: IPointDeVente = { id: 15959 };

      activatedRoute.data = of({ pointDeVente });
      comp.ngOnInit();

      expect(comp.pointDeVente).toEqual(pointDeVente);
    });
  });

  describe('save', () => {
    it('should call update service on save for existing entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IPointDeVente>>();
      const pointDeVente = { id: 30602 };
      jest.spyOn(pointDeVenteFormService, 'getPointDeVente').mockReturnValue(pointDeVente);
      jest.spyOn(pointDeVenteService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ pointDeVente });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: pointDeVente }));
      saveSubject.complete();

      // THEN
      expect(pointDeVenteFormService.getPointDeVente).toHaveBeenCalled();
      expect(comp.previousState).toHaveBeenCalled();
      expect(pointDeVenteService.update).toHaveBeenCalledWith(expect.objectContaining(pointDeVente));
      expect(comp.isSaving).toEqual(false);
    });

    it('should call create service on save for new entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IPointDeVente>>();
      const pointDeVente = { id: 30602 };
      jest.spyOn(pointDeVenteFormService, 'getPointDeVente').mockReturnValue({ id: null });
      jest.spyOn(pointDeVenteService, 'create').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ pointDeVente: null });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: pointDeVente }));
      saveSubject.complete();

      // THEN
      expect(pointDeVenteFormService.getPointDeVente).toHaveBeenCalled();
      expect(pointDeVenteService.create).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).toHaveBeenCalled();
    });

    it('should set isSaving to false on error', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IPointDeVente>>();
      const pointDeVente = { id: 30602 };
      jest.spyOn(pointDeVenteService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ pointDeVente });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.error('This is an error!');

      // THEN
      expect(pointDeVenteService.update).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).not.toHaveBeenCalled();
    });
  });
});
