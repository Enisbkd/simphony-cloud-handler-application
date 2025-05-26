import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpResponse, provideHttpClient } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Subject, from, of } from 'rxjs';

import { RemiseService } from '../service/remise.service';
import { IRemise } from '../remise.model';
import { RemiseFormService } from './remise-form.service';

import { RemiseUpdateComponent } from './remise-update.component';

describe('Remise Management Update Component', () => {
  let comp: RemiseUpdateComponent;
  let fixture: ComponentFixture<RemiseUpdateComponent>;
  let activatedRoute: ActivatedRoute;
  let remiseFormService: RemiseFormService;
  let remiseService: RemiseService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [RemiseUpdateComponent],
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
      .overrideTemplate(RemiseUpdateComponent, '')
      .compileComponents();

    fixture = TestBed.createComponent(RemiseUpdateComponent);
    activatedRoute = TestBed.inject(ActivatedRoute);
    remiseFormService = TestBed.inject(RemiseFormService);
    remiseService = TestBed.inject(RemiseService);

    comp = fixture.componentInstance;
  });

  describe('ngOnInit', () => {
    it('should update editForm', () => {
      const remise: IRemise = { id: 1654 };

      activatedRoute.data = of({ remise });
      comp.ngOnInit();

      expect(comp.remise).toEqual(remise);
    });
  });

  describe('save', () => {
    it('should call update service on save for existing entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IRemise>>();
      const remise = { id: 4087 };
      jest.spyOn(remiseFormService, 'getRemise').mockReturnValue(remise);
      jest.spyOn(remiseService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ remise });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: remise }));
      saveSubject.complete();

      // THEN
      expect(remiseFormService.getRemise).toHaveBeenCalled();
      expect(comp.previousState).toHaveBeenCalled();
      expect(remiseService.update).toHaveBeenCalledWith(expect.objectContaining(remise));
      expect(comp.isSaving).toEqual(false);
    });

    it('should call create service on save for new entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IRemise>>();
      const remise = { id: 4087 };
      jest.spyOn(remiseFormService, 'getRemise').mockReturnValue({ id: null });
      jest.spyOn(remiseService, 'create').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ remise: null });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: remise }));
      saveSubject.complete();

      // THEN
      expect(remiseFormService.getRemise).toHaveBeenCalled();
      expect(remiseService.create).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).toHaveBeenCalled();
    });

    it('should set isSaving to false on error', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IRemise>>();
      const remise = { id: 4087 };
      jest.spyOn(remiseService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ remise });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.error('This is an error!');

      // THEN
      expect(remiseService.update).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).not.toHaveBeenCalled();
    });
  });
});
