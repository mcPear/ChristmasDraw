import {BrowserModule} from "@angular/platform-browser";
import {APP_INITIALIZER, NgModule} from "@angular/core";
import {KeycloakAngularModule, KeycloakService} from "keycloak-angular";
import {initializer} from "./utils/app-init";
import {AppComponent} from "./app.component";
import {HttpClient, HttpClientModule} from '@angular/common/http';
import {BrowserAnimationsModule} from "@angular/platform-browser/animations";
import {MaterialModule} from "./material.module";
import {UserService} from "./shared/service/user.service";
import {UserEditComponent} from "./user-edit/user-edit.component";
import {MAT_DIALOG_DEFAULT_OPTIONS} from "@angular/material";
import {FormsModule} from '@angular/forms';
import {GroupsMemberComponent} from "./groups-member/groups-member.component";
import {GroupsOwnerComponent} from "./groups-owner/groups-owner.component";
import {GroupJoinComponent} from "./group-join/group-join.component";
import {GroupCreateComponent} from "./group-create/group-create.component";
import {UserDrawComponent} from "./group/user-draw/user-draw.component";
import {RequestsComponent} from "./group/requests/requests.component";
import {GroupContainerComponent} from "./group/group-container/group-container.component";
import {UserGroupComponent} from "./group/user-group/user-group.component";
import {UserComponent} from "./user/user.component";
import {MembersComponent} from "./group/members/members.component";
import {AdminPanelComponent} from "./admin-panel/admin-panel.component";
import {DenyRequestConfirmationComponent} from "./group/deny-request-confirmation/deny-request-confirmation.component";
import {UserManegerComponent} from './user-maneger/user-maneger.component';
import {AppCacheStorage} from "./shared/storage/app-cache-storage";
import {ImmutableDrawComponent} from "./group/immutable-draw/immutable-draw.component";
import {MutableDrawComponent} from "./group/mutable-draw/mutable-draw.component";
import {LeaveGroupConfirmationComponent} from "./group/leave-group-confirmation/leave-group-confirmation.component";
import {TranslateLoader, TranslateModule} from '@ngx-translate/core';
import {TranslateHttpLoader} from '@ngx-translate/http-loader';
import {AppLanguageStorage} from "./shared/storage/app-language-storage";
import {DeleteGroupConfirmationComponent} from "./delete-group-confirmation/delete-group-confirmation.component";
import {UserDrawModalComponent} from "./group/user-draw-modal/user-draw-modal.component";

export function HttpLoaderFactory(http: HttpClient) {
  return new TranslateHttpLoader(http);
}

@NgModule({
  declarations: [
    AppComponent,
    UserComponent,
    UserEditComponent,
    GroupsMemberComponent,
    GroupsOwnerComponent,
    GroupJoinComponent,
    GroupCreateComponent,
    UserDrawComponent,
    RequestsComponent,
    GroupContainerComponent,
    UserGroupComponent,
    MembersComponent,
    AdminPanelComponent,
    DenyRequestConfirmationComponent,
    LeaveGroupConfirmationComponent,
    UserManegerComponent,
    ImmutableDrawComponent,
    MutableDrawComponent,
    DeleteGroupConfirmationComponent,
    UserDrawModalComponent
  ],
  imports: [
    BrowserModule,
    KeycloakAngularModule,
    HttpClientModule,
    BrowserAnimationsModule,
    MaterialModule,
    FormsModule,
    TranslateModule.forRoot(),
    TranslateModule.forRoot({
      loader: {
        provide: TranslateLoader,
        useFactory: HttpLoaderFactory,
        deps: [HttpClient]
      }
    })
  ],
  providers: [
    {provide: MAT_DIALOG_DEFAULT_OPTIONS, useValue: {hasBackdrop: false}},
    UserService,
    AppCacheStorage,
    AppLanguageStorage,
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
    GroupCreateComponent,
    DenyRequestConfirmationComponent,
    LeaveGroupConfirmationComponent,
    DeleteGroupConfirmationComponent,
    UserDrawModalComponent
  ]
})
export class AppModule {
}
