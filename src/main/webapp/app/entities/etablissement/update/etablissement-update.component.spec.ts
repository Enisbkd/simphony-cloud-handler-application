import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpResponse, provideHttpClient } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Subject, from, of } from 'rxjs';

import { EtablissementService } from '../service/etablissement.service';
import { IEtablissement } from '../etablissement.model';
import { EtablissementFormService } from './etablissement-form.service';

import { EtablissementUpdateComponent } from './etablissement-update.component';

describe('Etablissement Management Update Component', () => {
  let comp: EtablissementUpdateComponent;
  let fixture: ComponentFixture<EtablissementUpdateComponent>;
  let activatedRoute: ActivatedRoute;
  let etablissementFormService: EtablissementFormService;
  let etablissementService: EtablissementService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [EtablissementUpdateComponent],
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
      .overrideTemplate(EtablissementUpdateComponent, '')
      .compileComponents();

    fixture = TestBed.createComponent(EtablissementUpdateComponent);
    activatedRoute = TestBed.inject(ActivatedRoute);
    etablissementFormService = TestBed.inject(EtablissementFormService);
    etablissementService = TestBed.inject(EtablissementService);

    comp = fixture.componentInstance;
  });

  describe('ngOnInit', () => {
    it('should update editForm', () => {
      const etablissement: IEtablissement = { id: '2af3dd96-06fc-459a-9ec9-11f9c9baa922' };

      activatedRoute.data = of({ etablissement });
      comp.ngOnInit();

      expect(comp.etablissement).toEqual(etablissement);
    });
  });

  describe('save', () => {
    it('should call update service on save for existing entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IEtablissement>>();
      const etablissement = { id: 'df0f23d6-3c73-4615-bf1e-9ca3de759dd5' };
      jest.spyOn(etablissementFormService, 'getEtablissement').mockReturnValue(etablissement);
      jest.spyOn(etablissementService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ etablissement });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: etablissement }));
      saveSubject.complete();

      // THEN
      expect(etablissementFormService.getEtablissement).toHaveBeenCalled();
      expect(comp.previousState).toHaveBeenCalled();
      expect(etablissementService.update).toHaveBeenCalledWith(expect.objectContaining(etablissement));
      expect(comp.isSaving).toEqual(false);
    });

    it('should call create service on save for new entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IEtablissement>>();
      const etablissement = { id: 'df0f23d6-3c73-4615-bf1e-9ca3de759dd5' };
      jest.spyOn(etablissementFormService, 'getEtablissement').mockReturnValue({ id: null });
      jest.spyOn(etablissementService, 'create').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ etablissement: null });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: etablissement }));
      saveSubject.complete();

      // THEN
      expect(etablissementFormService.getEtablissement).toHaveBeenCalled();
      expect(etablissementService.create).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).toHaveBeenCalled();
    });

    it('should set isSaving to false on error', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IEtablissement>>();
      const etablissement = { id: 'df0f23d6-3c73-4615-bf1e-9ca3de759dd5' };
      jest.spyOn(etablissementService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ etablissement });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.error('This is an error!');

      // THEN
      expect(etablissementService.update).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).not.toHaveBeenCalled();
    });
  });
});
