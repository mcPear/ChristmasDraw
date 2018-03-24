import {BrowserModule} from "@angular/platform-browser";
import {APP_INITIALIZER, NgModule} from "@angular/core";
import {KeycloakAngularModule, KeycloakService} from "keycloak-angular";
import {initializer} from "./utils/app-init";
import {AppComponent} from "./app.component";
import {AppService} from "./app.service";
import {HttpClientModule} from '@angular/common/http';
import {BrowserAnimationsModule} from "@angular/platform-browser/animations";
import {MaterialModule} from "./material.module";
import {UserComponent} from "./user/user.component";
import {UserService} from "./user/user.service";
import {UserEditComponent} from "./user-edit/user-edit.component";
import {MAT_DIALOG_DEFAULT_OPTIONS} from "@angular/material";


@NgModule({
  declarations: [
    AppComponent,
    UserComponent,
    UserEditComponent
  ],
  imports: [
    BrowserModule,
    KeycloakAngularModule,
    HttpClientModule,
    BrowserAnimationsModule,
    MaterialModule
  ],
  providers: [
    {provide: MAT_DIALOG_DEFAULT_OPTIONS, useValue: {hasBackdrop: false}},
    AppService,
    UserService,
    {
      provide: APP_INITIALIZER,
      useFactory: initializer,
      multi: true,
      deps: [KeycloakService]
    }
  ],
  bootstrap: [AppComponent],
  entryComponents: [
    UserEditComponent
  ]
})
export class AppModule {
}
