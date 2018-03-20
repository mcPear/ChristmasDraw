import {BrowserModule} from "@angular/platform-browser";
import {APP_INITIALIZER, NgModule} from "@angular/core";
import {KeycloakAngularModule, KeycloakService} from "keycloak-angular";
import {initializer} from "./utils/app-init";
import {AppComponent} from "./app.component";
import {AppService} from "./app.service";
import { HttpClientModule } from '@angular/common/http';


@NgModule({
  declarations: [
    AppComponent
  ],
  imports: [
    BrowserModule,
    KeycloakAngularModule,
    HttpClientModule
  ],
  providers: [
    AppService,
    {
      provide: APP_INITIALIZER,
      useFactory: initializer,
      multi: true,
      deps: [KeycloakService]
    }
  ],
  bootstrap: [AppComponent]
})
export class AppModule {
}
