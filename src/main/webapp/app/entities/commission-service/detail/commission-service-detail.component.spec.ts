import { ComponentFixture, TestBed } from '@angular/core/testing';
import { provideRouter, withComponentInputBinding } from '@angular/router';
import { RouterTestingHarness } from '@angular/router/testing';
import { of } from 'rxjs';

import { CommissionServiceDetailComponent } from './commission-service-detail.component';

describe('CommissionService Management Detail Component', () => {
  let comp: CommissionServiceDetailComponent;
  let fixture: ComponentFixture<CommissionServiceDetailComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [CommissionServiceDetailComponent],
      providers: [
        provideRouter(
          [
            {
              path: '**',
              loadComponent: () => import('./commission-service-detail.component').then(m => m.CommissionServiceDetailComponent),
              resolve: { commissionService: () => of({ id: 19824 }) },
            },
          ],
          withComponentInputBinding(),
        ),
      ],
    })
      .overrideTemplate(CommissionServiceDetailComponent, '')
      .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(CommissionServiceDetailComponent);
    comp = fixture.componentInstance;
  });

  describe('OnInit', () => {
    it('should load commissionService on init', async () => {
      const harness = await RouterTestingHarness.create();
      const instance = await harness.navigateByUrl('/', CommissionServiceDetailComponent);

      // THEN
      expect(instance.commissionService()).toEqual(expect.objectContaining({ id: 19824 }));
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
