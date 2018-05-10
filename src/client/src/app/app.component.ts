import {Component, OnInit} from '@angular/core';
import {KeycloakProfile} from 'keycloak-js';
import {KeycloakService} from 'keycloak-angular';
import {SelectedGroupData} from "./shared/model/selected-group-data";
import {AppCacheStorage} from "./shared/storage/app-cache-storage";

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

  constructor(private keycloakService: KeycloakService, private cacheStorage: AppCacheStorage) {
    this.isAdmin = false;
  }

  async ngOnInit() {
    if (!this.userDetails) {
      this.keycloakService.loadUserProfile()
        .then(res => {
          this.cacheStorage.initForUser(res.username)
          this.userDetails = res
          this.notifyBackendAboutUser()
          this.loadGroupsWhereOwner()
        })
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

  IsAdmin() {
    var roles = this.keycloakService.getUserRoles(true);
    roles.forEach(value => {
      if (value.localeCompare('admin') == 0)
        this.isAdmin = true;
    });
  }

  async loadGroupsWhereOwner() {
    this.groupsWhereOwner = await this.cacheStorage.getGroupsWhereOwner();
  }

  async notifyBackendAboutUser() {
    await this.cacheStorage.getUserDto();
  }
}
