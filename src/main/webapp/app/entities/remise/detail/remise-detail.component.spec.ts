import { ComponentFixture, TestBed } from '@angular/core/testing';
import { provideRouter, withComponentInputBinding } from '@angular/router';
import { RouterTestingHarness } from '@angular/router/testing';
import { of } from 'rxjs';

import { RemiseDetailComponent } from './remise-detail.component';

describe('Remise Management Detail Component', () => {
  let comp: RemiseDetailComponent;
  let fixture: ComponentFixture<RemiseDetailComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [RemiseDetailComponent],
      providers: [
        provideRouter(
          [
            {
              path: '**',
              loadComponent: () => import('./remise-detail.component').then(m => m.RemiseDetailComponent),
              resolve: { remise: () => of({ id: 4087 }) },
            },
          ],
          withComponentInputBinding(),
        ),
      ],
    })
      .overrideTemplate(RemiseDetailComponent, '')
      .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(RemiseDetailComponent);
    comp = fixture.componentInstance;
  });

  describe('OnInit', () => {
    it('should load remise on init', async () => {
      const harness = await RouterTestingHarness.create();
      const instance = await harness.navigateByUrl('/', RemiseDetailComponent);

      // THEN
      expect(instance.remise()).toEqual(expect.objectContaining({ id: 4087 }));
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
