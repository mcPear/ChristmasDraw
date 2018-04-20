import {Component, OnInit} from '@angular/core';
import {KeycloakProfile} from 'keycloak-js';
import {KeycloakService} from 'keycloak-angular';
import {UserService} from "./shared/service/user.service";
import {SelectedGroupData} from "./shared/model/selected-group-data";

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.css']
})
export class AppComponent implements OnInit {
  title = 'ChristmasDrawApp';
  userDetails: KeycloakProfile;
  groupsWhereOwner: string[];
  selectedGroup: SelectedGroupData;
  isAdmin: boolean;

  constructor(private keycloakService: KeycloakService, private service: UserService) {
    this.isAdmin = false;
  }

  async ngOnInit() {
    if (!this.userDetails) {
      this.userDetails = await this.keycloakService.loadUserProfile();
    }
    this.service.getUser(this.userDetails.username);
    if (!this.groupsWhereOwner) {
      this.groupsWhereOwner = await this.service.getOwnerGroups();
    }
    this.IsAdmin();
  }

  async doLogout() {
    await this.keycloakService.logout();
  }

  deselectGroup() {
    this.selectedGroup = undefined;
  }

  selectGroup(name: string, isOwned: boolean) { //FIXME causes reloading and requests
    this.selectedGroup = <SelectedGroupData>{name: name, isOwned: isOwned};
  }

  getSelectedGroupOutput() {
    if (this.selectedGroup)
      return ' in ' + this.selectedGroup.name;
    else
      return "";
  }

  IsAdmin(){
    var roles = this.keycloakService.getUserRoles(true);
    roles.forEach(value => {
      if(value.localeCompare('admin') == 0)
        this.isAdmin = true;
    });
  }
}
