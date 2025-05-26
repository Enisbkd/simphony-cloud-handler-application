import { ComponentFixture, TestBed } from '@angular/core/testing';
import { provideRouter, withComponentInputBinding } from '@angular/router';
import { RouterTestingHarness } from '@angular/router/testing';
import { of } from 'rxjs';

import { FamilyGroupDetailComponent } from './family-group-detail.component';

describe('FamilyGroup Management Detail Component', () => {
  let comp: FamilyGroupDetailComponent;
  let fixture: ComponentFixture<FamilyGroupDetailComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [FamilyGroupDetailComponent],
      providers: [
        provideRouter(
          [
            {
              path: '**',
              loadComponent: () => import('./family-group-detail.component').then(m => m.FamilyGroupDetailComponent),
              resolve: { familyGroup: () => of({ id: 28164 }) },
            },
          ],
          withComponentInputBinding(),
        ),
      ],
    })
      .overrideTemplate(FamilyGroupDetailComponent, '')
      .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(FamilyGroupDetailComponent);
    comp = fixture.componentInstance;
  });

  describe('OnInit', () => {
    it('should load familyGroup on init', async () => {
      const harness = await RouterTestingHarness.create();
      const instance = await harness.navigateByUrl('/', FamilyGroupDetailComponent);

      // THEN
      expect(instance.familyGroup()).toEqual(expect.objectContaining({ id: 28164 }));
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
