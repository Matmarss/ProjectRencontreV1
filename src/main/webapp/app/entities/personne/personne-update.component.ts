import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';

import { IPersonne, Personne } from 'app/shared/model/personne.model';
import { PersonneService } from './personne.service';

@Component({
  selector: 'jhi-personne-update',
  templateUrl: './personne-update.component.html'
})
export class PersonneUpdateComponent implements OnInit {
  isSaving = false;

  editForm = this.fb.group({
    id: [],
    nom: [null, [Validators.required]],
    prenom: [null, [Validators.required]],
    mail: [null, [Validators.required]],
    genre: [null, [Validators.required]],
    motDePasse: [null, [Validators.required]],
    naissance: [null, [Validators.required]],
    listCarac: []
  });

  constructor(protected personneService: PersonneService, protected activatedRoute: ActivatedRoute, private fb: FormBuilder) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ personne }) => {
      this.updateForm(personne);
    });
  }

  updateForm(personne: IPersonne): void {
    this.editForm.patchValue({
      id: personne.id,
      nom: personne.nom,
      prenom: personne.prenom,
      mail: personne.mail,
      genre: personne.genre,
      motDePasse: personne.motDePasse,
      naissance: personne.naissance,
      listCarac: personne.listCarac
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const personne = this.createFromForm();
    if (personne.id !== undefined) {
      this.subscribeToSaveResponse(this.personneService.update(personne));
    } else {
      this.subscribeToSaveResponse(this.personneService.create(personne));
    }
  }

  private createFromForm(): IPersonne {
    return {
      ...new Personne(),
      id: this.editForm.get(['id'])!.value,
      nom: this.editForm.get(['nom'])!.value,
      prenom: this.editForm.get(['prenom'])!.value,
      mail: this.editForm.get(['mail'])!.value,
      genre: this.editForm.get(['genre'])!.value,
      motDePasse: this.editForm.get(['motDePasse'])!.value,
      naissance: this.editForm.get(['naissance'])!.value,
      listCarac: this.editForm.get(['listCarac'])!.value
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IPersonne>>): void {
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
}
