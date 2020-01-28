import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';

import './vendor';
import { RencontreV1SharedModule } from 'app/shared/shared.module';
import { RencontreV1CoreModule } from 'app/core/core.module';
import { RencontreV1AppRoutingModule } from './app-routing.module';
import { RencontreV1HomeModule } from './home/home.module';
import { RencontreV1EntityModule } from './entities/entity.module';
// jhipster-needle-angular-add-module-import JHipster will add new module here
import { MainComponent } from './layouts/main/main.component';
import { NavbarComponent } from './layouts/navbar/navbar.component';
import { FooterComponent } from './layouts/footer/footer.component';
import { PageRibbonComponent } from './layouts/profiles/page-ribbon.component';
import { ErrorComponent } from './layouts/error/error.component';

@NgModule({
  imports: [
    BrowserModule,
    RencontreV1SharedModule,
    RencontreV1CoreModule,
    RencontreV1HomeModule,
    // jhipster-needle-angular-add-module JHipster will add new module here
    RencontreV1EntityModule,
    RencontreV1AppRoutingModule
  ],
  declarations: [MainComponent, NavbarComponent, ErrorComponent, PageRibbonComponent, FooterComponent],
  bootstrap: [MainComponent]
})
export class RencontreV1AppModule {}
