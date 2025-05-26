import { ComponentFixture, TestBed } from '@angular/core/testing';
import { provideRouter, withComponentInputBinding } from '@angular/router';
import { RouterTestingHarness } from '@angular/router/testing';
import { of } from 'rxjs';

import { SocieteDetailComponent } from './societe-detail.component';

describe('Societe Management Detail Component', () => {
  let comp: SocieteDetailComponent;
  let fixture: ComponentFixture<SocieteDetailComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [SocieteDetailComponent],
      providers: [
        provideRouter(
          [
            {
              path: '**',
              loadComponent: () => import('./societe-detail.component').then(m => m.SocieteDetailComponent),
              resolve: { societe: () => of({ id: '2f3b41cc-f98b-49dd-975d-0b2e24547e80' }) },
            },
          ],
          withComponentInputBinding(),
        ),
      ],
    })
      .overrideTemplate(SocieteDetailComponent, '')
      .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(SocieteDetailComponent);
    comp = fixture.componentInstance;
  });

  describe('OnInit', () => {
    it('should load societe on init', async () => {
      const harness = await RouterTestingHarness.create();
      const instance = await harness.navigateByUrl('/', SocieteDetailComponent);

      // THEN
      expect(instance.societe()).toEqual(expect.objectContaining({ id: '2f3b41cc-f98b-49dd-975d-0b2e24547e80' }));
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
