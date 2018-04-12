import {Component, OnInit} from '@angular/core';
import {KeycloakProfile} from 'keycloak-js';
import {KeycloakService} from 'keycloak-angular';
import {UserService} from "./shared/service/user.service";

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.css']
})
export class AppComponent implements OnInit {
  title = 'ChristmasDrawApp';
  userDetails: KeycloakProfile;
  groupsWhereOwner: string[];
  selectedGroup: string;

  constructor(private keycloakService: KeycloakService, private service: UserService) {
  }

  async ngOnInit() {
    if (!this.userDetails) {
      this.userDetails = await this.keycloakService.loadUserProfile();
    }
    this.service.getUser(this.userDetails.username);
    if (!this.groupsWhereOwner) {
      this.groupsWhereOwner = await this.service.getOwnerGroups();
    }
  }

  async doLogout() {
    await this.keycloakService.logout();
  }

  deselectGroup() {
    this.selectedGroup = undefined;
  }

  selectGroup(name: string) { //FIXME causes reloading and requests
    this.selectedGroup = name;
  }

  getSelectedGroupOutput() {
    if (this.selectedGroup)
      return ' in '+this.selectedGroup;
    else
      return "";
  }
}
