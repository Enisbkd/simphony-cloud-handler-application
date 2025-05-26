import { ComponentFixture, TestBed } from '@angular/core/testing';
import { provideRouter, withComponentInputBinding } from '@angular/router';
import { RouterTestingHarness } from '@angular/router/testing';
import { of } from 'rxjs';

import { BarcodeDetailComponent } from './barcode-detail.component';

describe('Barcode Management Detail Component', () => {
  let comp: BarcodeDetailComponent;
  let fixture: ComponentFixture<BarcodeDetailComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [BarcodeDetailComponent],
      providers: [
        provideRouter(
          [
            {
              path: '**',
              loadComponent: () => import('./barcode-detail.component').then(m => m.BarcodeDetailComponent),
              resolve: { barcode: () => of({ id: 17083 }) },
            },
          ],
          withComponentInputBinding(),
        ),
      ],
    })
      .overrideTemplate(BarcodeDetailComponent, '')
      .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(BarcodeDetailComponent);
    comp = fixture.componentInstance;
  });

  describe('OnInit', () => {
    it('should load barcode on init', async () => {
      const harness = await RouterTestingHarness.create();
      const instance = await harness.navigateByUrl('/', BarcodeDetailComponent);

      // THEN
      expect(instance.barcode()).toEqual(expect.objectContaining({ id: 17083 }));
    });
  });

  describe('PreviousState', () => {
    it('should navigate to previous state', () => {
      jest.spyOn(window.history, 'back');
      comp.previousState();
      expect(window.history.back).toHaveBeenCalled();
    });
  });
});
