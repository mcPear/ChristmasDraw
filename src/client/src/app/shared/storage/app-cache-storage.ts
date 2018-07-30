import {UserDto} from "../dto/user.dto";
import {Injectable} from "@angular/core";
import {UserService} from "../service/user.service";
import * as SecureLS from "../../../../node_modules/secure-ls/dist/secure-ls";

@Injectable()

export class AppCacheStorage {
  ownedGroupsPrefix = 'ownedGroups';
  userDtoPrefix = 'userDto';
  ownedGroupsKey: string;
  userDtoKey: string;
  username: string;
  secureLS = new SecureLS({encodingType: 'aes', isCompression: false});

  constructor(private userService: UserService) {
  }

  initForUser(username: string) {
    this.ownedGroupsKey = this.ownedGroupsPrefix + '-' + username;
    this.userDtoKey = this.userDtoPrefix + '-' + username;
    this.username = username;
  }

  async getOwnedGroups(): Promise<string[]> {
    let cachedGroupsStringified = this.secureLS.get(this.ownedGroupsKey);
    if (!cachedGroupsStringified) {
      return this.fetchOwnedGroups()
    } else {
      return JSON.parse(cachedGroupsStringified
      )
    }
  }

  private async fetchOwnedGroups(): Promise<string[]> {
    let serviceGroups = await this.userService.getOwnedGroups();
    this.cacheGroups(serviceGroups);
    return serviceGroups
  }

  getUserDto(): Promise<UserDto> {
    let cachedUserDtoStringified = this.secureLS.get(this.userDtoKey);
    if (!this.secureLS.get(this.userDtoKey)) {
      return this.fetchUserDto()
    } else {
      return JSON.parse(cachedUserDtoStringified)
    }
  }

  private async fetchUserDto(): Promise<UserDto> {
    let userDto = await this.userService.getUser(this.username);
    this.secureLS.set(this.userDtoKey, JSON.stringify(userDto as UserDto));
    return userDto
  }


  async updateUserDto(userDto: UserDto) {
    this.secureLS.set(this.userDtoKey, JSON.stringify(userDto));
    await this.userService.updateUser(userDto)
  }

  async addOwnedGroup(group: string) {
    let groups = await this.getOwnedGroups();
    groups.push(group);
    this.cacheGroups(groups);
    this.userService.createGroup(group)
  }

  public async deleteGroup(name: string) {
    this.userService.deleteGroup(name);
    let cachedGroupsStringified = this.secureLS.get(this.ownedGroupsKey);
    if (!cachedGroupsStringified) {
      this.cacheGroups([name]);
    } else {
      let cachedGroups = JSON.parse(cachedGroupsStringified);
      this.removeGroup(cachedGroups, name);
      this.cacheGroups(cachedGroups);
    }
  }

  private removeGroup(list: string[], name: string) {
    let index = list.indexOf(name, 0);
    if (index > -1) {
      list.splice(index, 1);
    }
  }

  private cacheGroups(names: string[]) {
    this.secureLS.set(this.ownedGroupsKey, JSON.stringify(names as string[]));
  }

}
