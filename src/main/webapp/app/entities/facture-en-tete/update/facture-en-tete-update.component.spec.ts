import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpResponse, provideHttpClient } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Subject, from, of } from 'rxjs';

import { FactureEnTeteService } from '../service/facture-en-tete.service';
import { IFactureEnTete } from '../facture-en-tete.model';
import { FactureEnTeteFormService } from './facture-en-tete-form.service';

import { FactureEnTeteUpdateComponent } from './facture-en-tete-update.component';

describe('FactureEnTete Management Update Component', () => {
  let comp: FactureEnTeteUpdateComponent;
  let fixture: ComponentFixture<FactureEnTeteUpdateComponent>;
  let activatedRoute: ActivatedRoute;
  let factureEnTeteFormService: FactureEnTeteFormService;
  let factureEnTeteService: FactureEnTeteService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [FactureEnTeteUpdateComponent],
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
      .overrideTemplate(FactureEnTeteUpdateComponent, '')
      .compileComponents();

    fixture = TestBed.createComponent(FactureEnTeteUpdateComponent);
    activatedRoute = TestBed.inject(ActivatedRoute);
    factureEnTeteFormService = TestBed.inject(FactureEnTeteFormService);
    factureEnTeteService = TestBed.inject(FactureEnTeteService);

    comp = fixture.componentInstance;
  });

  describe('ngOnInit', () => {
    it('should update editForm', () => {
      const factureEnTete: IFactureEnTete = { id: 18092 };

      activatedRoute.data = of({ factureEnTete });
      comp.ngOnInit();

      expect(comp.factureEnTete).toEqual(factureEnTete);
    });
  });

  describe('save', () => {
    it('should call update service on save for existing entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IFactureEnTete>>();
      const factureEnTete = { id: 22488 };
      jest.spyOn(factureEnTeteFormService, 'getFactureEnTete').mockReturnValue(factureEnTete);
      jest.spyOn(factureEnTeteService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ factureEnTete });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: factureEnTete }));
      saveSubject.complete();

      // THEN
      expect(factureEnTeteFormService.getFactureEnTete).toHaveBeenCalled();
      expect(comp.previousState).toHaveBeenCalled();
      expect(factureEnTeteService.update).toHaveBeenCalledWith(expect.objectContaining(factureEnTete));
      expect(comp.isSaving).toEqual(false);
    });

    it('should call create service on save for new entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IFactureEnTete>>();
      const factureEnTete = { id: 22488 };
      jest.spyOn(factureEnTeteFormService, 'getFactureEnTete').mockReturnValue({ id: null });
      jest.spyOn(factureEnTeteService, 'create').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ factureEnTete: null });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: factureEnTete }));
      saveSubject.complete();

      // THEN
      expect(factureEnTeteFormService.getFactureEnTete).toHaveBeenCalled();
      expect(factureEnTeteService.create).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).toHaveBeenCalled();
    });

    it('should set isSaving to false on error', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IFactureEnTete>>();
      const factureEnTete = { id: 22488 };
      jest.spyOn(factureEnTeteService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ factureEnTete });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.error('This is an error!');

      // THEN
      expect(factureEnTeteService.update).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).not.toHaveBeenCalled();
    });
  });
});
