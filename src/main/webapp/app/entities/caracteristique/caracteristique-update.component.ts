import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';

import { ICaracteristique, Caracteristique } from 'app/shared/model/caracteristique.model';
import { CaracteristiqueService } from './caracteristique.service';
import { IPersonne } from 'app/shared/model/personne.model';
import { PersonneService } from 'app/entities/personne/personne.service';

@Component({
  selector: 'jhi-caracteristique-update',
  templateUrl: './caracteristique-update.component.html'
})
export class CaracteristiqueUpdateComponent implements OnInit {
  isSaving = false;

  personnes: IPersonne[] = [];

  editForm = this.fb.group({
    id: [],
    carac: [],
    personne: []
  });

  constructor(
    protected caracteristiqueService: CaracteristiqueService,
    protected personneService: PersonneService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ caracteristique }) => {
      this.updateForm(caracteristique);

      this.personneService
        .query()
        .pipe(
          map((res: HttpResponse<IPersonne[]>) => {
            return res.body ? res.body : [];
          })
        )
        .subscribe((resBody: IPersonne[]) => (this.personnes = resBody));
    });
  }

  updateForm(caracteristique: ICaracteristique): void {
    this.editForm.patchValue({
      id: caracteristique.id,
      carac: caracteristique.carac,
      personne: caracteristique.personne
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const caracteristique = this.createFromForm();
    if (caracteristique.id !== undefined) {
      this.subscribeToSaveResponse(this.caracteristiqueService.update(caracteristique));
    } else {
      this.subscribeToSaveResponse(this.caracteristiqueService.create(caracteristique));
    }
  }

  private createFromForm(): ICaracteristique {
    return {
      ...new Caracteristique(),
      id: this.editForm.get(['id'])!.value,
      carac: this.editForm.get(['carac'])!.value,
      personne: this.editForm.get(['personne'])!.value
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<ICaracteristique>>): void {
    result.subscribe(
      () => this.onSaveSuccess(),
      () => this.onSaveError()
    );
  }

  protected onSaveSuccess(): void {
    this.isSaving = false;
    this.previousState();
  }

  protected onSaveError(): void {
    this.isSaving = false;
  }

  trackById(index: number, item: IPersonne): any {
    return item.id;
  }
}
