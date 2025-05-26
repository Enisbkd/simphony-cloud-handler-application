import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpResponse, provideHttpClient } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Subject, from, of } from 'rxjs';

import { ElementMenuService } from '../service/element-menu.service';
import { IElementMenu } from '../element-menu.model';
import { ElementMenuFormService } from './element-menu-form.service';

import { ElementMenuUpdateComponent } from './element-menu-update.component';

describe('ElementMenu Management Update Component', () => {
  let comp: ElementMenuUpdateComponent;
  let fixture: ComponentFixture<ElementMenuUpdateComponent>;
  let activatedRoute: ActivatedRoute;
  let elementMenuFormService: ElementMenuFormService;
  let elementMenuService: ElementMenuService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [ElementMenuUpdateComponent],
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
      .overrideTemplate(ElementMenuUpdateComponent, '')
      .compileComponents();

    fixture = TestBed.createComponent(ElementMenuUpdateComponent);
    activatedRoute = TestBed.inject(ActivatedRoute);
    elementMenuFormService = TestBed.inject(ElementMenuFormService);
    elementMenuService = TestBed.inject(ElementMenuService);

    comp = fixture.componentInstance;
  });

  describe('ngOnInit', () => {
    it('should update editForm', () => {
      const elementMenu: IElementMenu = { id: 1836 };

      activatedRoute.data = of({ elementMenu });
      comp.ngOnInit();

      expect(comp.elementMenu).toEqual(elementMenu);
    });
  });

  describe('save', () => {
    it('should call update service on save for existing entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IElementMenu>>();
      const elementMenu = { id: 25075 };
      jest.spyOn(elementMenuFormService, 'getElementMenu').mockReturnValue(elementMenu);
      jest.spyOn(elementMenuService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ elementMenu });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: elementMenu }));
      saveSubject.complete();

      // THEN
      expect(elementMenuFormService.getElementMenu).toHaveBeenCalled();
      expect(comp.previousState).toHaveBeenCalled();
      expect(elementMenuService.update).toHaveBeenCalledWith(expect.objectContaining(elementMenu));
      expect(comp.isSaving).toEqual(false);
    });

    it('should call create service on save for new entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IElementMenu>>();
      const elementMenu = { id: 25075 };
      jest.spyOn(elementMenuFormService, 'getElementMenu').mockReturnValue({ id: null });
      jest.spyOn(elementMenuService, 'create').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ elementMenu: null });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: elementMenu }));
      saveSubject.complete();

      // THEN
      expect(elementMenuFormService.getElementMenu).toHaveBeenCalled();
      expect(elementMenuService.create).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).toHaveBeenCalled();
    });

    it('should set isSaving to false on error', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IElementMenu>>();
      const elementMenu = { id: 25075 };
      jest.spyOn(elementMenuService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ elementMenu });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.error('This is an error!');

      // THEN
      expect(elementMenuService.update).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).not.toHaveBeenCalled();
    });
  });
});
