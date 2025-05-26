import { ComponentFixture, TestBed } from '@angular/core/testing';
import { provideRouter, withComponentInputBinding } from '@angular/router';
import { RouterTestingHarness } from '@angular/router/testing';
import { of } from 'rxjs';

import { PointDeVenteDetailComponent } from './point-de-vente-detail.component';

describe('PointDeVente Management Detail Component', () => {
  let comp: PointDeVenteDetailComponent;
  let fixture: ComponentFixture<PointDeVenteDetailComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [PointDeVenteDetailComponent],
      providers: [
        provideRouter(
          [
            {
              path: '**',
              loadComponent: () => import('./point-de-vente-detail.component').then(m => m.PointDeVenteDetailComponent),
              resolve: { pointDeVente: () => of({ id: 30602 }) },
            },
          ],
          withComponentInputBinding(),
        ),
      ],
    })
      .overrideTemplate(PointDeVenteDetailComponent, '')
      .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(PointDeVenteDetailComponent);
    comp = fixture.componentInstance;
  });

  describe('OnInit', () => {
    it('should load pointDeVente on init', async () => {
      const harness = await RouterTestingHarness.create();
      const instance = await harness.navigateByUrl('/', PointDeVenteDetailComponent);

      // THEN
      expect(instance.pointDeVente()).toEqual(expect.objectContaining({ id: 30602 }));
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
