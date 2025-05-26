import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpResponse, provideHttpClient } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Subject, from, of } from 'rxjs';

import { BarcodeService } from '../service/barcode.service';
import { IBarcode } from '../barcode.model';
import { BarcodeFormService } from './barcode-form.service';

import { BarcodeUpdateComponent } from './barcode-update.component';

describe('Barcode Management Update Component', () => {
  let comp: BarcodeUpdateComponent;
  let fixture: ComponentFixture<BarcodeUpdateComponent>;
  let activatedRoute: ActivatedRoute;
  let barcodeFormService: BarcodeFormService;
  let barcodeService: BarcodeService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [BarcodeUpdateComponent],
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
      .overrideTemplate(BarcodeUpdateComponent, '')
      .compileComponents();

    fixture = TestBed.createComponent(BarcodeUpdateComponent);
    activatedRoute = TestBed.inject(ActivatedRoute);
    barcodeFormService = TestBed.inject(BarcodeFormService);
    barcodeService = TestBed.inject(BarcodeService);

    comp = fixture.componentInstance;
  });

  describe('ngOnInit', () => {
    it('should update editForm', () => {
      const barcode: IBarcode = { id: 18666 };

      activatedRoute.data = of({ barcode });
      comp.ngOnInit();

      expect(comp.barcode).toEqual(barcode);
    });
  });

  describe('save', () => {
    it('should call update service on save for existing entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IBarcode>>();
      const barcode = { id: 17083 };
      jest.spyOn(barcodeFormService, 'getBarcode').mockReturnValue(barcode);
      jest.spyOn(barcodeService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ barcode });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: barcode }));
      saveSubject.complete();

      // THEN
      expect(barcodeFormService.getBarcode).toHaveBeenCalled();
      expect(comp.previousState).toHaveBeenCalled();
      expect(barcodeService.update).toHaveBeenCalledWith(expect.objectContaining(barcode));
      expect(comp.isSaving).toEqual(false);
    });

    it('should call create service on save for new entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IBarcode>>();
      const barcode = { id: 17083 };
      jest.spyOn(barcodeFormService, 'getBarcode').mockReturnValue({ id: null });
      jest.spyOn(barcodeService, 'create').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ barcode: null });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: barcode }));
      saveSubject.complete();

      // THEN
      expect(barcodeFormService.getBarcode).toHaveBeenCalled();
      expect(barcodeService.create).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).toHaveBeenCalled();
    });

    it('should set isSaving to false on error', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IBarcode>>();
      const barcode = { id: 17083 };
      jest.spyOn(barcodeService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ barcode });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.error('This is an error!');

      // THEN
      expect(barcodeService.update).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).not.toHaveBeenCalled();
    });
  });
});
