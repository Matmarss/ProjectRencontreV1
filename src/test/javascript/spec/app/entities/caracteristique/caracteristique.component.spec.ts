import { ComponentFixture, TestBed } from '@angular/core/testing';
import { of } from 'rxjs';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { RencontreV1TestModule } from '../../../test.module';
import { CaracteristiqueComponent } from 'app/entities/caracteristique/caracteristique.component';
import { CaracteristiqueService } from 'app/entities/caracteristique/caracteristique.service';
import { Caracteristique } from 'app/shared/model/caracteristique.model';

describe('Component Tests', () => {
  describe('Caracteristique Management Component', () => {
    let comp: CaracteristiqueComponent;
    let fixture: ComponentFixture<CaracteristiqueComponent>;
    let service: CaracteristiqueService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [RencontreV1TestModule],
        declarations: [CaracteristiqueComponent],
        providers: []
      })
        .overrideTemplate(CaracteristiqueComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(CaracteristiqueComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(CaracteristiqueService);
    });

    it('Should call load all on init', () => {
      // GIVEN
      const headers = new HttpHeaders().append('link', 'link;link');
      spyOn(service, 'query').and.returnValue(
        of(
          new HttpResponse({
            body: [new Caracteristique(123)],
            headers
          })
        )
      );

      // WHEN
      comp.ngOnInit();

      // THEN
      expect(service.query).toHaveBeenCalled();
      expect(comp.caracteristiques && comp.caracteristiques[0]).toEqual(jasmine.objectContaining({ id: 123 }));
    });
  });
});
