import { Component, OnInit } from '@angular/core';
import { KeycloakProfile } from 'keycloak-js';
import {KeycloakOptions, KeycloakService} from 'keycloak-angular';
import {UserService} from "./shared/service/user.service";
import {GroupsDto} from "./shared/dto/groups.dto";

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.css']
})
export class AppComponent implements OnInit {
  title='ChristmasDraw';
  userDetails: KeycloakProfile;

  constructor(private keycloakService: KeycloakService) {}

  async ngOnInit() {
    this.userDetails = await this.keycloakService.loadUserProfile();
  }

  async doLogout() {
    await this.keycloakService.logout();
  }
}
