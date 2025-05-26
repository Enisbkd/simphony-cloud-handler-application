import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpResponse, provideHttpClient } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Subject, from, of } from 'rxjs';

import { ModePaiementService } from '../service/mode-paiement.service';
import { IModePaiement } from '../mode-paiement.model';
import { ModePaiementFormService } from './mode-paiement-form.service';

import { ModePaiementUpdateComponent } from './mode-paiement-update.component';

describe('ModePaiement Management Update Component', () => {
  let comp: ModePaiementUpdateComponent;
  let fixture: ComponentFixture<ModePaiementUpdateComponent>;
  let activatedRoute: ActivatedRoute;
  let modePaiementFormService: ModePaiementFormService;
  let modePaiementService: ModePaiementService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [ModePaiementUpdateComponent],
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
      .overrideTemplate(ModePaiementUpdateComponent, '')
      .compileComponents();

    fixture = TestBed.createComponent(ModePaiementUpdateComponent);
    activatedRoute = TestBed.inject(ActivatedRoute);
    modePaiementFormService = TestBed.inject(ModePaiementFormService);
    modePaiementService = TestBed.inject(ModePaiementService);

    comp = fixture.componentInstance;
  });

  describe('ngOnInit', () => {
    it('should update editForm', () => {
      const modePaiement: IModePaiement = { id: 32260 };

      activatedRoute.data = of({ modePaiement });
      comp.ngOnInit();

      expect(comp.modePaiement).toEqual(modePaiement);
    });
  });

  describe('save', () => {
    it('should call update service on save for existing entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IModePaiement>>();
      const modePaiement = { id: 18358 };
      jest.spyOn(modePaiementFormService, 'getModePaiement').mockReturnValue(modePaiement);
      jest.spyOn(modePaiementService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ modePaiement });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: modePaiement }));
      saveSubject.complete();

      // THEN
      expect(modePaiementFormService.getModePaiement).toHaveBeenCalled();
      expect(comp.previousState).toHaveBeenCalled();
      expect(modePaiementService.update).toHaveBeenCalledWith(expect.objectContaining(modePaiement));
      expect(comp.isSaving).toEqual(false);
    });

    it('should call create service on save for new entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IModePaiement>>();
      const modePaiement = { id: 18358 };
      jest.spyOn(modePaiementFormService, 'getModePaiement').mockReturnValue({ id: null });
      jest.spyOn(modePaiementService, 'create').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ modePaiement: null });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: modePaiement }));
      saveSubject.complete();

      // THEN
      expect(modePaiementFormService.getModePaiement).toHaveBeenCalled();
      expect(modePaiementService.create).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).toHaveBeenCalled();
    });

    it('should set isSaving to false on error', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IModePaiement>>();
      const modePaiement = { id: 18358 };
      jest.spyOn(modePaiementService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ modePaiement });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.error('This is an error!');

      // THEN
      expect(modePaiementService.update).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).not.toHaveBeenCalled();
    });
  });
});
