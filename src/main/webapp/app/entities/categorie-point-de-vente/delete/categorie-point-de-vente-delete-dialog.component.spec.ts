jest.mock('@ng-bootstrap/ng-bootstrap');

import { ComponentFixture, TestBed, fakeAsync, inject, tick } from '@angular/core/testing';
import { HttpResponse, provideHttpClient } from '@angular/common/http';
import { of } from 'rxjs';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';

import { CategoriePointDeVenteService } from '../service/categorie-point-de-vente.service';

import { CategoriePointDeVenteDeleteDialogComponent } from './categorie-point-de-vente-delete-dialog.component';

describe('CategoriePointDeVente Management Delete Component', () => {
  let comp: CategoriePointDeVenteDeleteDialogComponent;
  let fixture: ComponentFixture<CategoriePointDeVenteDeleteDialogComponent>;
  let service: CategoriePointDeVenteService;
  let mockActiveModal: NgbActiveModal;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [CategoriePointDeVenteDeleteDialogComponent],
      providers: [provideHttpClient(), NgbActiveModal],
    })
      .overrideTemplate(CategoriePointDeVenteDeleteDialogComponent, '')
      .compileComponents();
    fixture = TestBed.createComponent(CategoriePointDeVenteDeleteDialogComponent);
    comp = fixture.componentInstance;
    service = TestBed.inject(CategoriePointDeVenteService);
    mockActiveModal = TestBed.inject(NgbActiveModal);
  });

  describe('confirmDelete', () => {
    it('should call delete service on confirmDelete', inject(
      [],
      fakeAsync(() => {
        // GIVEN
        jest.spyOn(service, 'delete').mockReturnValue(of(new HttpResponse({ body: {} })));

        // WHEN
        comp.confirmDelete(123);
        tick();

        // THEN
        expect(service.delete).toHaveBeenCalledWith(123);
        expect(mockActiveModal.close).toHaveBeenCalledWith('deleted');
      }),
    ));

    it('should not call delete service on clear', () => {
      // GIVEN
      jest.spyOn(service, 'delete');

      // WHEN
      comp.cancel();

      // THEN
      expect(service.delete).not.toHaveBeenCalled();
      expect(mockActiveModal.close).not.toHaveBeenCalled();
      expect(mockActiveModal.dismiss).toHaveBeenCalled();
    });
  });
});
