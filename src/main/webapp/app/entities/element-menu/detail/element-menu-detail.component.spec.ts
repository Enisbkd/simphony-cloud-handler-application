import { ComponentFixture, TestBed } from '@angular/core/testing';
import { provideRouter, withComponentInputBinding } from '@angular/router';
import { RouterTestingHarness } from '@angular/router/testing';
import { of } from 'rxjs';

import { ElementMenuDetailComponent } from './element-menu-detail.component';

describe('ElementMenu Management Detail Component', () => {
  let comp: ElementMenuDetailComponent;
  let fixture: ComponentFixture<ElementMenuDetailComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [ElementMenuDetailComponent],
      providers: [
        provideRouter(
          [
            {
              path: '**',
              loadComponent: () => import('./element-menu-detail.component').then(m => m.ElementMenuDetailComponent),
              resolve: { elementMenu: () => of({ id: 25075 }) },
            },
          ],
          withComponentInputBinding(),
        ),
      ],
    })
      .overrideTemplate(ElementMenuDetailComponent, '')
      .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(ElementMenuDetailComponent);
    comp = fixture.componentInstance;
  });

  describe('OnInit', () => {
    it('should load elementMenu on init', async () => {
      const harness = await RouterTestingHarness.create();
      const instance = await harness.navigateByUrl('/', ElementMenuDetailComponent);

      // THEN
      expect(instance.elementMenu()).toEqual(expect.objectContaining({ id: 25075 }));
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
