import { ComponentFixture, TestBed } from '@angular/core/testing';
import { provideRouter, withComponentInputBinding } from '@angular/router';
import { RouterTestingHarness } from '@angular/router/testing';
import { of } from 'rxjs';

import { HierDetailComponent } from './hier-detail.component';

describe('Hier Management Detail Component', () => {
  let comp: HierDetailComponent;
  let fixture: ComponentFixture<HierDetailComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [HierDetailComponent],
      providers: [
        provideRouter(
          [
            {
              path: '**',
              loadComponent: () => import('./hier-detail.component').then(m => m.HierDetailComponent),
              resolve: { hier: () => of({ id: '5b7fea44-24f3-4956-abaa-0999154e9c91' }) },
            },
          ],
          withComponentInputBinding(),
        ),
      ],
    })
      .overrideTemplate(HierDetailComponent, '')
      .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(HierDetailComponent);
    comp = fixture.componentInstance;
  });

  describe('OnInit', () => {
    it('should load hier on init', async () => {
      const harness = await RouterTestingHarness.create();
      const instance = await harness.navigateByUrl('/', HierDetailComponent);

      // THEN
      expect(instance.hier()).toEqual(expect.objectContaining({ id: '5b7fea44-24f3-4956-abaa-0999154e9c91' }));
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
