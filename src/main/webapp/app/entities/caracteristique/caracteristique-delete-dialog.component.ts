import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { ICaracteristique } from 'app/shared/model/caracteristique.model';
import { CaracteristiqueService } from './caracteristique.service';

@Component({
  templateUrl: './caracteristique-delete-dialog.component.html'
})
export class CaracteristiqueDeleteDialogComponent {
  caracteristique?: ICaracteristique;

  constructor(
    protected caracteristiqueService: CaracteristiqueService,
    public activeModal: NgbActiveModal,
    protected eventManager: JhiEventManager
  ) {}

  clear(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.caracteristiqueService.delete(id).subscribe(() => {
      this.eventManager.broadcast('caracteristiqueListModification');
      this.activeModal.close();
    });
  }
}
