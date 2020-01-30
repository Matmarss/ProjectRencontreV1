import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';

import { ICaracteristique } from 'app/shared/model/caracteristique.model';
import { CaracteristiqueService } from './caracteristique.service';
import { CaracteristiqueDeleteDialogComponent } from './caracteristique-delete-dialog.component';

@Component({
  selector: 'jhi-caracteristique',
  templateUrl: './caracteristique.component.html'
})
export class CaracteristiqueComponent implements OnInit, OnDestroy {
  caracteristiques?: ICaracteristique[];
  eventSubscriber?: Subscription;

  constructor(
    protected caracteristiqueService: CaracteristiqueService,
    protected eventManager: JhiEventManager,
    protected modalService: NgbModal
  ) {}

  loadAll(): void {
    this.caracteristiqueService.query().subscribe((res: HttpResponse<ICaracteristique[]>) => {
      this.caracteristiques = res.body ? res.body : [];
    });
  }

  ngOnInit(): void {
    this.loadAll();
    this.registerChangeInCaracteristiques();
  }

  ngOnDestroy(): void {
    if (this.eventSubscriber) {
      this.eventManager.destroy(this.eventSubscriber);
    }
  }

  trackId(index: number, item: ICaracteristique): number {
    // eslint-disable-next-line @typescript-eslint/no-unnecessary-type-assertion
    return item.id!;
  }

  registerChangeInCaracteristiques(): void {
    this.eventSubscriber = this.eventManager.subscribe('caracteristiqueListModification', () => this.loadAll());
  }

  delete(caracteristique: ICaracteristique): void {
    const modalRef = this.modalService.open(CaracteristiqueDeleteDialogComponent, { size: 'lg', backdrop: 'static' });
    modalRef.componentInstance.caracteristique = caracteristique;
  }
}
