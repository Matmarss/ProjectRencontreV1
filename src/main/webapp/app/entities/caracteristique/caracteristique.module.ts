import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { RencontreV1SharedModule } from 'app/shared/shared.module';
import { CaracteristiqueComponent } from './caracteristique.component';
import { CaracteristiqueDetailComponent } from './caracteristique-detail.component';
import { CaracteristiqueUpdateComponent } from './caracteristique-update.component';
import { CaracteristiqueDeleteDialogComponent } from './caracteristique-delete-dialog.component';
import { caracteristiqueRoute } from './caracteristique.route';

@NgModule({
  imports: [RencontreV1SharedModule, RouterModule.forChild(caracteristiqueRoute)],
  declarations: [
    CaracteristiqueComponent,
    CaracteristiqueDetailComponent,
    CaracteristiqueUpdateComponent,
    CaracteristiqueDeleteDialogComponent
  ],
  entryComponents: [CaracteristiqueDeleteDialogComponent]
})
export class RencontreV1CaracteristiqueModule {}
