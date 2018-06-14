import {Component, OnInit} from '@angular/core';
import {KeycloakProfile} from 'keycloak-js';
import {KeycloakService} from 'keycloak-angular';
import {SelectedGroup} from "./shared/model/selected-group-data";
import {AppCacheStorage} from "./shared/storage/app-cache-storage";
import {TranslateService} from "@ngx-translate/core";

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.css']
})
export class AppComponent implements OnInit {
  title = 'ChristmasDrawApp';
  userDetails: KeycloakProfile;
  groupsWhereOwner: string[];
  selectedGroup: SelectedGroup;
  isAdmin: boolean;
  param = {value: 'world'};

  constructor(private keycloakService: KeycloakService, private cacheStorage: AppCacheStorage, translate: TranslateService) {
    this.isAdmin = false;
    translate.setDefaultLang('en');
    translate.use('en');
  }

  async ngOnInit() {
    if (!this.userDetails) {
      this.keycloakService.loadUserProfile()
        .then(userProfile => {
          this.initAfterUserProfileFetch(userProfile)
        })
    }
    this.setIsAdmin();
  }

  initAfterUserProfileFetch(userProfile: KeycloakProfile) {
    this.cacheStorage.initForUser(userProfile.username);
    this.userDetails = userProfile;
    this.notifyBackendAboutUser().then(
      () => this.loadGroupsWhereOwner()
    );
  }

  async doLogout() {
    await this.keycloakService.logout();
  }

  deselectGroup() {
    this.selectedGroup = undefined;
  }

  selectGroup(name: string, isOwned: boolean) {
    this.selectedGroup = <SelectedGroup>{name: name, isOwned: isOwned};
  }

  getSelectedGroupOutput() {
    if (this.selectedGroup)
      return ' in ' + this.selectedGroup.name;
    else
      return "";
  }

  setIsAdmin() {
    let roles = this.keycloakService.getUserRoles(true);
    roles.forEach(value => {
      if (value.localeCompare('admin') == 0)
        this.isAdmin = true;
    });
  }

  async loadGroupsWhereOwner() {
    this.groupsWhereOwner = await this.cacheStorage.getOwnedGroups();
  }

  async notifyBackendAboutUser() {
    await this.cacheStorage.getUserDto();
  }
}
