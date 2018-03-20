import { Component, OnInit } from '@angular/core';
import { KeycloakProfile } from 'keycloak-js';
import {KeycloakOptions, KeycloakService} from 'keycloak-angular';
import {AppService} from "./app.service";

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.css']
})
export class AppComponent implements OnInit {
  title='ChristmasDraw';
  userDetails: KeycloakProfile;
  fetchedUserNickname: String;

  constructor(private keycloakService: KeycloakService, private service: AppService) {}

  async ngOnInit() {
    this.userDetails = await this.keycloakService.loadUserProfile();
    this.service.fetchUserNickname()
      .then( res => this.fetchedUserNickname = res)
      .catch(res => console.log(res));
  }

  async doLogout() {
    await this.keycloakService.logout();
  }
}
