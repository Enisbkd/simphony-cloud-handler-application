import { ComponentFixture, TestBed } from '@angular/core/testing';
import { provideRouter, withComponentInputBinding } from '@angular/router';
import { RouterTestingHarness } from '@angular/router/testing';
import { of } from 'rxjs';

import { CategoriePointDeVenteDetailComponent } from './categorie-point-de-vente-detail.component';

describe('CategoriePointDeVente Management Detail Component', () => {
  let comp: CategoriePointDeVenteDetailComponent;
  let fixture: ComponentFixture<CategoriePointDeVenteDetailComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [CategoriePointDeVenteDetailComponent],
      providers: [
        provideRouter(
          [
            {
              path: '**',
              loadComponent: () => import('./categorie-point-de-vente-detail.component').then(m => m.CategoriePointDeVenteDetailComponent),
              resolve: { categoriePointDeVente: () => of({ id: 6431 }) },
            },
          ],
          withComponentInputBinding(),
        ),
      ],
    })
      .overrideTemplate(CategoriePointDeVenteDetailComponent, '')
      .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(CategoriePointDeVenteDetailComponent);
    comp = fixture.componentInstance;
  });

  describe('OnInit', () => {
    it('should load categoriePointDeVente on init', async () => {
      const harness = await RouterTestingHarness.create();
      const instance = await harness.navigateByUrl('/', CategoriePointDeVenteDetailComponent);

      // THEN
      expect(instance.categoriePointDeVente()).toEqual(expect.objectContaining({ id: 6431 }));
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
