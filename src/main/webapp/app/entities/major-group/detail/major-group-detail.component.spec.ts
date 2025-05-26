import { ComponentFixture, TestBed } from '@angular/core/testing';
import { provideRouter, withComponentInputBinding } from '@angular/router';
import { RouterTestingHarness } from '@angular/router/testing';
import { of } from 'rxjs';

import { MajorGroupDetailComponent } from './major-group-detail.component';

describe('MajorGroup Management Detail Component', () => {
  let comp: MajorGroupDetailComponent;
  let fixture: ComponentFixture<MajorGroupDetailComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [MajorGroupDetailComponent],
      providers: [
        provideRouter(
          [
            {
              path: '**',
              loadComponent: () => import('./major-group-detail.component').then(m => m.MajorGroupDetailComponent),
              resolve: { majorGroup: () => of({ id: 5994 }) },
            },
          ],
          withComponentInputBinding(),
        ),
      ],
    })
      .overrideTemplate(MajorGroupDetailComponent, '')
      .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(MajorGroupDetailComponent);
    comp = fixture.componentInstance;
  });

  describe('OnInit', () => {
    it('should load majorGroup on init', async () => {
      const harness = await RouterTestingHarness.create();
      const instance = await harness.navigateByUrl('/', MajorGroupDetailComponent);

      // THEN
      expect(instance.majorGroup()).toEqual(expect.objectContaining({ id: 5994 }));
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
