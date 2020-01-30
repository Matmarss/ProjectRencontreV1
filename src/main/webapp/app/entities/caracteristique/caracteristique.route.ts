import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Routes, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { flatMap } from 'rxjs/operators';

import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { ICaracteristique, Caracteristique } from 'app/shared/model/caracteristique.model';
import { CaracteristiqueService } from './caracteristique.service';
import { CaracteristiqueComponent } from './caracteristique.component';
import { CaracteristiqueDetailComponent } from './caracteristique-detail.component';
import { CaracteristiqueUpdateComponent } from './caracteristique-update.component';

@Injectable({ providedIn: 'root' })
export class CaracteristiqueResolve implements Resolve<ICaracteristique> {
  constructor(private service: CaracteristiqueService, private router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<ICaracteristique> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        flatMap((caracteristique: HttpResponse<Caracteristique>) => {
          if (caracteristique.body) {
            return of(caracteristique.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new Caracteristique());
  }
}

export const caracteristiqueRoute: Routes = [
  {
    path: '',
    component: CaracteristiqueComponent,
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'Caracteristiques'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/view',
    component: CaracteristiqueDetailComponent,
    resolve: {
      caracteristique: CaracteristiqueResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'Caracteristiques'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: 'new',
    component: CaracteristiqueUpdateComponent,
    resolve: {
      caracteristique: CaracteristiqueResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'Caracteristiques'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/edit',
    component: CaracteristiqueUpdateComponent,
    resolve: {
      caracteristique: CaracteristiqueResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'Caracteristiques'
    },
    canActivate: [UserRouteAccessService]
  }
];
