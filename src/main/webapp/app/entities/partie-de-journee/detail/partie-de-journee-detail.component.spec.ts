import { ComponentFixture, TestBed } from '@angular/core/testing';
import { provideRouter, withComponentInputBinding } from '@angular/router';
import { RouterTestingHarness } from '@angular/router/testing';
import { of } from 'rxjs';

import { PartieDeJourneeDetailComponent } from './partie-de-journee-detail.component';

describe('PartieDeJournee Management Detail Component', () => {
  let comp: PartieDeJourneeDetailComponent;
  let fixture: ComponentFixture<PartieDeJourneeDetailComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [PartieDeJourneeDetailComponent],
      providers: [
        provideRouter(
          [
            {
              path: '**',
              loadComponent: () => import('./partie-de-journee-detail.component').then(m => m.PartieDeJourneeDetailComponent),
              resolve: { partieDeJournee: () => of({ id: 24117 }) },
            },
          ],
          withComponentInputBinding(),
        ),
      ],
    })
      .overrideTemplate(PartieDeJourneeDetailComponent, '')
      .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(PartieDeJourneeDetailComponent);
    comp = fixture.componentInstance;
  });

  describe('OnInit', () => {
    it('should load partieDeJournee on init', async () => {
      const harness = await RouterTestingHarness.create();
      const instance = await harness.navigateByUrl('/', PartieDeJourneeDetailComponent);

      // THEN
      expect(instance.partieDeJournee()).toEqual(expect.objectContaining({ id: 24117 }));
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
