import { ComponentFixture, TestBed } from '@angular/core/testing';
import { provideRouter, withComponentInputBinding } from '@angular/router';
import { RouterTestingHarness } from '@angular/router/testing';
import { of } from 'rxjs';

import { EtablissementDetailComponent } from './etablissement-detail.component';

describe('Etablissement Management Detail Component', () => {
  let comp: EtablissementDetailComponent;
  let fixture: ComponentFixture<EtablissementDetailComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [EtablissementDetailComponent],
      providers: [
        provideRouter(
          [
            {
              path: '**',
              loadComponent: () => import('./etablissement-detail.component').then(m => m.EtablissementDetailComponent),
              resolve: { etablissement: () => of({ id: 'df0f23d6-3c73-4615-bf1e-9ca3de759dd5' }) },
            },
          ],
          withComponentInputBinding(),
        ),
      ],
    })
      .overrideTemplate(EtablissementDetailComponent, '')
      .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(EtablissementDetailComponent);
    comp = fixture.componentInstance;
  });

  describe('OnInit', () => {
    it('should load etablissement on init', async () => {
      const harness = await RouterTestingHarness.create();
      const instance = await harness.navigateByUrl('/', EtablissementDetailComponent);

      // THEN
      expect(instance.etablissement()).toEqual(expect.objectContaining({ id: 'df0f23d6-3c73-4615-bf1e-9ca3de759dd5' }));
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
