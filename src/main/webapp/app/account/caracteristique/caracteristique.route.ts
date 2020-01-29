import { Route } from '@angular/router';

import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { CaracteristiqueComponent } from './caracteristique.component';

export const caracteristiqueRoute: Route = {
  path: 'caracteristique',
  component: CaracteristiqueComponent,
  data: {
    authorities: ['ROLE_USER'],
    pageTitle: 'caracteristique'
  },
  canActivate: [UserRouteAccessService]
};
