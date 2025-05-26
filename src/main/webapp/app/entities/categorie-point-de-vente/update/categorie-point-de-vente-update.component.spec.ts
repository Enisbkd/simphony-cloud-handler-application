import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpResponse, provideHttpClient } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Subject, from, of } from 'rxjs';

import { CategoriePointDeVenteService } from '../service/categorie-point-de-vente.service';
import { ICategoriePointDeVente } from '../categorie-point-de-vente.model';
import { CategoriePointDeVenteFormService } from './categorie-point-de-vente-form.service';

import { CategoriePointDeVenteUpdateComponent } from './categorie-point-de-vente-update.component';

describe('CategoriePointDeVente Management Update Component', () => {
  let comp: CategoriePointDeVenteUpdateComponent;
  let fixture: ComponentFixture<CategoriePointDeVenteUpdateComponent>;
  let activatedRoute: ActivatedRoute;
  let categoriePointDeVenteFormService: CategoriePointDeVenteFormService;
  let categoriePointDeVenteService: CategoriePointDeVenteService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [CategoriePointDeVenteUpdateComponent],
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
      .overrideTemplate(CategoriePointDeVenteUpdateComponent, '')
      .compileComponents();

    fixture = TestBed.createComponent(CategoriePointDeVenteUpdateComponent);
    activatedRoute = TestBed.inject(ActivatedRoute);
    categoriePointDeVenteFormService = TestBed.inject(CategoriePointDeVenteFormService);
    categoriePointDeVenteService = TestBed.inject(CategoriePointDeVenteService);

    comp = fixture.componentInstance;
  });

  describe('ngOnInit', () => {
    it('should update editForm', () => {
      const categoriePointDeVente: ICategoriePointDeVente = { id: 11623 };

      activatedRoute.data = of({ categoriePointDeVente });
      comp.ngOnInit();

      expect(comp.categoriePointDeVente).toEqual(categoriePointDeVente);
    });
  });

  describe('save', () => {
    it('should call update service on save for existing entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<ICategoriePointDeVente>>();
      const categoriePointDeVente = { id: 6431 };
      jest.spyOn(categoriePointDeVenteFormService, 'getCategoriePointDeVente').mockReturnValue(categoriePointDeVente);
      jest.spyOn(categoriePointDeVenteService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ categoriePointDeVente });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: categoriePointDeVente }));
      saveSubject.complete();

      // THEN
      expect(categoriePointDeVenteFormService.getCategoriePointDeVente).toHaveBeenCalled();
      expect(comp.previousState).toHaveBeenCalled();
      expect(categoriePointDeVenteService.update).toHaveBeenCalledWith(expect.objectContaining(categoriePointDeVente));
      expect(comp.isSaving).toEqual(false);
    });

    it('should call create service on save for new entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<ICategoriePointDeVente>>();
      const categoriePointDeVente = { id: 6431 };
      jest.spyOn(categoriePointDeVenteFormService, 'getCategoriePointDeVente').mockReturnValue({ id: null });
      jest.spyOn(categoriePointDeVenteService, 'create').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ categoriePointDeVente: null });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: categoriePointDeVente }));
      saveSubject.complete();

      // THEN
      expect(categoriePointDeVenteFormService.getCategoriePointDeVente).toHaveBeenCalled();
      expect(categoriePointDeVenteService.create).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).toHaveBeenCalled();
    });

    it('should set isSaving to false on error', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<ICategoriePointDeVente>>();
      const categoriePointDeVente = { id: 6431 };
      jest.spyOn(categoriePointDeVenteService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ categoriePointDeVente });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.error('This is an error!');

      // THEN
      expect(categoriePointDeVenteService.update).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).not.toHaveBeenCalled();
    });
  });
});
