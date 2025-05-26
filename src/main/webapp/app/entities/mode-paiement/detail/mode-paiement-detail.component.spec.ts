import { ComponentFixture, TestBed } from '@angular/core/testing';
import { provideRouter, withComponentInputBinding } from '@angular/router';
import { RouterTestingHarness } from '@angular/router/testing';
import { of } from 'rxjs';

import { ModePaiementDetailComponent } from './mode-paiement-detail.component';

describe('ModePaiement Management Detail Component', () => {
  let comp: ModePaiementDetailComponent;
  let fixture: ComponentFixture<ModePaiementDetailComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [ModePaiementDetailComponent],
      providers: [
        provideRouter(
          [
            {
              path: '**',
              loadComponent: () => import('./mode-paiement-detail.component').then(m => m.ModePaiementDetailComponent),
              resolve: { modePaiement: () => of({ id: 18358 }) },
            },
          ],
          withComponentInputBinding(),
        ),
      ],
    })
      .overrideTemplate(ModePaiementDetailComponent, '')
      .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(ModePaiementDetailComponent);
    comp = fixture.componentInstance;
  });

  describe('OnInit', () => {
    it('should load modePaiement on init', async () => {
      const harness = await RouterTestingHarness.create();
      const instance = await harness.navigateByUrl('/', ModePaiementDetailComponent);

      // THEN
      expect(instance.modePaiement()).toEqual(expect.objectContaining({ id: 18358 }));
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
