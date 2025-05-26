import { ComponentFixture, TestBed } from '@angular/core/testing';
import { provideRouter, withComponentInputBinding } from '@angular/router';
import { RouterTestingHarness } from '@angular/router/testing';
import { of } from 'rxjs';

import { FactureEnTeteDetailComponent } from './facture-en-tete-detail.component';

describe('FactureEnTete Management Detail Component', () => {
  let comp: FactureEnTeteDetailComponent;
  let fixture: ComponentFixture<FactureEnTeteDetailComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [FactureEnTeteDetailComponent],
      providers: [
        provideRouter(
          [
            {
              path: '**',
              loadComponent: () => import('./facture-en-tete-detail.component').then(m => m.FactureEnTeteDetailComponent),
              resolve: { factureEnTete: () => of({ id: 22488 }) },
            },
          ],
          withComponentInputBinding(),
        ),
      ],
    })
      .overrideTemplate(FactureEnTeteDetailComponent, '')
      .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(FactureEnTeteDetailComponent);
    comp = fixture.componentInstance;
  });

  describe('OnInit', () => {
    it('should load factureEnTete on init', async () => {
      const harness = await RouterTestingHarness.create();
      const instance = await harness.navigateByUrl('/', FactureEnTeteDetailComponent);

      // THEN
      expect(instance.factureEnTete()).toEqual(expect.objectContaining({ id: 22488 }));
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
