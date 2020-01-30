import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { RencontreV1TestModule } from '../../../test.module';
import { CaracteristiqueDetailComponent } from 'app/entities/caracteristique/caracteristique-detail.component';
import { Caracteristique } from 'app/shared/model/caracteristique.model';

describe('Component Tests', () => {
  describe('Caracteristique Management Detail Component', () => {
    let comp: CaracteristiqueDetailComponent;
    let fixture: ComponentFixture<CaracteristiqueDetailComponent>;
    const route = ({ data: of({ caracteristique: new Caracteristique(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [RencontreV1TestModule],
        declarations: [CaracteristiqueDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }]
      })
        .overrideTemplate(CaracteristiqueDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(CaracteristiqueDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should load caracteristique on init', () => {
        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.caracteristique).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
