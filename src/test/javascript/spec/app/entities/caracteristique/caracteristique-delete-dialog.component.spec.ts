import { ComponentFixture, TestBed, inject, fakeAsync, tick } from '@angular/core/testing';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { of } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';

import { RencontreV1TestModule } from '../../../test.module';
import { MockEventManager } from '../../../helpers/mock-event-manager.service';
import { MockActiveModal } from '../../../helpers/mock-active-modal.service';
import { CaracteristiqueDeleteDialogComponent } from 'app/entities/caracteristique/caracteristique-delete-dialog.component';
import { CaracteristiqueService } from 'app/entities/caracteristique/caracteristique.service';

describe('Component Tests', () => {
  describe('Caracteristique Management Delete Component', () => {
    let comp: CaracteristiqueDeleteDialogComponent;
    let fixture: ComponentFixture<CaracteristiqueDeleteDialogComponent>;
    let service: CaracteristiqueService;
    let mockEventManager: MockEventManager;
    let mockActiveModal: MockActiveModal;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [RencontreV1TestModule],
        declarations: [CaracteristiqueDeleteDialogComponent]
      })
        .overrideTemplate(CaracteristiqueDeleteDialogComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(CaracteristiqueDeleteDialogComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(CaracteristiqueService);
      mockEventManager = TestBed.get(JhiEventManager);
      mockActiveModal = TestBed.get(NgbActiveModal);
    });

    describe('confirmDelete', () => {
      it('Should call delete service on confirmDelete', inject(
        [],
        fakeAsync(() => {
          // GIVEN
          spyOn(service, 'delete').and.returnValue(of({}));

          // WHEN
          comp.confirmDelete(123);
          tick();

          // THEN
          expect(service.delete).toHaveBeenCalledWith(123);
          expect(mockActiveModal.closeSpy).toHaveBeenCalled();
          expect(mockEventManager.broadcastSpy).toHaveBeenCalled();
        })
      ));
      it('Should not call delete service on clear', () => {
        // GIVEN
        spyOn(service, 'delete');

        // WHEN
        comp.clear();

        // THEN
        expect(service.delete).not.toHaveBeenCalled();
        expect(mockActiveModal.dismissSpy).toHaveBeenCalled();
      });
    });
  });
});
