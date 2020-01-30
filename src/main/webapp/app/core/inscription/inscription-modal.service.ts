import { Injectable } from '@angular/core';
import { NgbModalRef, NgbModal } from '@ng-bootstrap/ng-bootstrap';
import { InscriptionModalComponent } from 'app/shared/inscription/inscription.component';

@Injectable({ providedIn: 'root' })
export class InscriptionModalService {
  private isOpen = false;

  constructor(private modalService: NgbModal) {}

  openopen(): void {
    if (this.isOpen) {
      return;
    }
    this.isOpen = true;
    const modalRef: NgbModalRef = this.modalService.open(InscriptionModalComponent);
    modalRef.result.finally(() => (this.isOpen = false));
  }
}
