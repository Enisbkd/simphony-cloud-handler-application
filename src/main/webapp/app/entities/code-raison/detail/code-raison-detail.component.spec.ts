import { ComponentFixture, TestBed } from '@angular/core/testing';
import { provideRouter, withComponentInputBinding } from '@angular/router';
import { RouterTestingHarness } from '@angular/router/testing';
import { of } from 'rxjs';

import { CodeRaisonDetailComponent } from './code-raison-detail.component';

describe('CodeRaison Management Detail Component', () => {
  let comp: CodeRaisonDetailComponent;
  let fixture: ComponentFixture<CodeRaisonDetailComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [CodeRaisonDetailComponent],
      providers: [
        provideRouter(
          [
            {
              path: '**',
              loadComponent: () => import('./code-raison-detail.component').then(m => m.CodeRaisonDetailComponent),
              resolve: { codeRaison: () => of({ id: 29266 }) },
            },
          ],
          withComponentInputBinding(),
        ),
      ],
    })
      .overrideTemplate(CodeRaisonDetailComponent, '')
      .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(CodeRaisonDetailComponent);
    comp = fixture.componentInstance;
  });

  describe('OnInit', () => {
    it('should load codeRaison on init', async () => {
      const harness = await RouterTestingHarness.create();
      const instance = await harness.navigateByUrl('/', CodeRaisonDetailComponent);

      // THEN
      expect(instance.codeRaison()).toEqual(expect.objectContaining({ id: 29266 }));
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
