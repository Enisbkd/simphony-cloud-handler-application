import { ComponentFixture, TestBed } from '@angular/core/testing';
import { provideRouter, withComponentInputBinding } from '@angular/router';
import { RouterTestingHarness } from '@angular/router/testing';
import { of } from 'rxjs';

import { EmployeDetailComponent } from './employe-detail.component';

describe('Employe Management Detail Component', () => {
  let comp: EmployeDetailComponent;
  let fixture: ComponentFixture<EmployeDetailComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [EmployeDetailComponent],
      providers: [
        provideRouter(
          [
            {
              path: '**',
              loadComponent: () => import('./employe-detail.component').then(m => m.EmployeDetailComponent),
              resolve: { employe: () => of({ id: 10360 }) },
            },
          ],
          withComponentInputBinding(),
        ),
      ],
    })
      .overrideTemplate(EmployeDetailComponent, '')
      .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(EmployeDetailComponent);
    comp = fixture.componentInstance;
  });

  describe('OnInit', () => {
    it('should load employe on init', async () => {
      const harness = await RouterTestingHarness.create();
      const instance = await harness.navigateByUrl('/', EmployeDetailComponent);

      // THEN
      expect(instance.employe()).toEqual(expect.objectContaining({ id: 10360 }));
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
