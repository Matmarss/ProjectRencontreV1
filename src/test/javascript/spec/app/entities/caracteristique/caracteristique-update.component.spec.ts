import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { of } from 'rxjs';

import { RencontreV1TestModule } from '../../../test.module';
import { CaracteristiqueUpdateComponent } from 'app/entities/caracteristique/caracteristique-update.component';
import { CaracteristiqueService } from 'app/entities/caracteristique/caracteristique.service';
import { Caracteristique } from 'app/shared/model/caracteristique.model';

describe('Component Tests', () => {
  describe('Caracteristique Management Update Component', () => {
    let comp: CaracteristiqueUpdateComponent;
    let fixture: ComponentFixture<CaracteristiqueUpdateComponent>;
    let service: CaracteristiqueService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [RencontreV1TestModule],
        declarations: [CaracteristiqueUpdateComponent],
        providers: [FormBuilder]
      })
        .overrideTemplate(CaracteristiqueUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(CaracteristiqueUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(CaracteristiqueService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new Caracteristique(123);
        spyOn(service, 'update').and.returnValue(of(new HttpResponse({ body: entity })));
        comp.updateForm(entity);
        // WHEN
        comp.save();
        tick(); // simulate async

        // THEN
        expect(service.update).toHaveBeenCalledWith(entity);
        expect(comp.isSaving).toEqual(false);
      }));

      it('Should call create service on save for new entity', fakeAsync(() => {
        // GIVEN
        const entity = new Caracteristique();
        spyOn(service, 'create').and.returnValue(of(new HttpResponse({ body: entity })));
        comp.updateForm(entity);
        // WHEN
        comp.save();
        tick(); // simulate async

        // THEN
        expect(service.create).toHaveBeenCalledWith(entity);
        expect(comp.isSaving).toEqual(false);
      }));
    });
  });
});
