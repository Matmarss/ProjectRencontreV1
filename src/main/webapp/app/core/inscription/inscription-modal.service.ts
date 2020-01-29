import { Injectable } from '@angular/core';
import { NgbModalRef, NgbModal } from '@ng-bootstrap/ng-bootstrap';
import { HomeComponent } from 'app/home/home.component';

@Injectable({ providedIn: 'root' })
export class InscriptionModalService {
  private isOpen = false;

  constructor(private modalService: NgbModal) {}

  open(): void {
    if (this.isOpen) {
      return;
    }
    this.isOpen = true;
    const modalRef: NgbModalRef = this.modalService.open(HomeComponent);
    modalRef.result.finally(() => (this.isOpen = false));
  }
}
