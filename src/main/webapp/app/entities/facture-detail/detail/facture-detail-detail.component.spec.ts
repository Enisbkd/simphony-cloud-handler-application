import { ComponentFixture, TestBed } from '@angular/core/testing';
import { provideRouter, withComponentInputBinding } from '@angular/router';
import { RouterTestingHarness } from '@angular/router/testing';
import { of } from 'rxjs';

import { FactureDetailDetailComponent } from './facture-detail-detail.component';

describe('FactureDetail Management Detail Component', () => {
  let comp: FactureDetailDetailComponent;
  let fixture: ComponentFixture<FactureDetailDetailComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [FactureDetailDetailComponent],
      providers: [
        provideRouter(
          [
            {
              path: '**',
              loadComponent: () => import('./facture-detail-detail.component').then(m => m.FactureDetailDetailComponent),
              resolve: { factureDetail: () => of({ id: 27600 }) },
            },
          ],
          withComponentInputBinding(),
        ),
      ],
    })
      .overrideTemplate(FactureDetailDetailComponent, '')
      .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(FactureDetailDetailComponent);
    comp = fixture.componentInstance;
  });

  describe('OnInit', () => {
    it('should load factureDetail on init', async () => {
      const harness = await RouterTestingHarness.create();
      const instance = await harness.navigateByUrl('/', FactureDetailDetailComponent);

      // THEN
      expect(instance.factureDetail()).toEqual(expect.objectContaining({ id: 27600 }));
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
