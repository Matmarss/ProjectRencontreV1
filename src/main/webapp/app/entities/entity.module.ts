import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

@NgModule({
  imports: [
    RouterModule.forChild([
      {
        path: 'personne',
        loadChildren: () => import('./personne/personne.module').then(m => m.RencontreV1PersonneModule)
      },
      {
        path: 'caracteristique',
        loadChildren: () => import('./caracteristique/caracteristique.module').then(m => m.RencontreV1CaracteristiqueModule)
      }
      /* jhipster-needle-add-entity-route - JHipster will add entity modules routes here */
    ])
  ]
})
export class RencontreV1EntityModule {}
