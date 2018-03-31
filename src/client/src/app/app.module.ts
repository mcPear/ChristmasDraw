import {BrowserModule} from "@angular/platform-browser";
import {APP_INITIALIZER, NgModule} from "@angular/core";
import {KeycloakAngularModule, KeycloakService} from "keycloak-angular";
import {initializer} from "./utils/app-init";
import {AppComponent} from "./app.component";
import {HttpClientModule} from '@angular/common/http';
import {BrowserAnimationsModule} from "@angular/platform-browser/animations";
import {MaterialModule} from "./material.module";
import {UserComponent} from "./user/user.component";
import {UserService} from "./shared/service/user.service";
import {UserEditComponent} from "./user-edit/user-edit.component";
import {MAT_DIALOG_DEFAULT_OPTIONS} from "@angular/material";
import { FormsModule }   from '@angular/forms';
import {GroupsMemberComponent} from "./groups-member/groups-member.component";
import {GroupsOwnerComponent} from "./groups-owner/groups-owner.component";
import {GroupJoinComponent} from "./group-join/group-join.component";
import {GroupCreateComponent} from "./group-create/group-create.component";

@NgModule({
  declarations: [
    AppComponent,
    UserComponent,
    UserEditComponent,
    GroupsMemberComponent,
    GroupsOwnerComponent,
    GroupJoinComponent,
    GroupCreateComponent
  ],
  imports: [
    BrowserModule,
    KeycloakAngularModule,
    HttpClientModule,
    BrowserAnimationsModule,
    MaterialModule,
    FormsModule
  ],
  providers: [
    {provide: MAT_DIALOG_DEFAULT_OPTIONS, useValue: {hasBackdrop: false}},
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
    UserEditComponent,
    GroupJoinComponent,
    GroupCreateComponent
  ]
})
export class AppModule {
}
