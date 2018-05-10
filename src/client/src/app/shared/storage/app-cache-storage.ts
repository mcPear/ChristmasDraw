import {UserDto} from "../dto/user.dto";
import {Injectable} from "@angular/core";
import {UserService} from "../service/user.service";

@Injectable()

export class AppCacheStorage {
  ownedGroupsPrefix = 'ownedGroups';
  userDtoPrefix = 'userDto';
  ownedGroupsKey: string;
  userDtoKey: string;
  username: string;

  constructor(private userService: UserService) {
  }

  initForUser(username: string) {
    this.ownedGroupsKey = this.ownedGroupsPrefix + '-' + username;
    this.userDtoKey = this.userDtoPrefix + '-' + username;
    this.username = username;
  }

  async getOwnedGroups(): Promise<string[]> {
    let cachedGroups = JSON.parse(localStorage.getItem(this.ownedGroupsKey));
    if (cachedGroups === null) {
      return this.fetchOwnedGroups()
    } else {
      return cachedGroups
    }
  }

  private async fetchOwnedGroups(): Promise<string[]> {
    let serviceGroups = await this.userService.getOwnedGroups();
    localStorage.setItem(this.ownedGroupsKey, JSON.stringify(serviceGroups as string[]));
    return serviceGroups
  }

  getUserDto(): Promise<UserDto> {
    let cachedUserDto = JSON.parse(localStorage.getItem(this.userDtoKey));
    if (cachedUserDto === null) {
      return this.fetchUserDto()
    } else {
      return cachedUserDto
    }
  }

  private async fetchUserDto(): Promise<UserDto> {
    let userDto = await this.userService.getUser(this.username);
    localStorage.setItem(this.userDtoKey, JSON.stringify(userDto as UserDto));
    return userDto
  }


  async updateUserDto(userDto: UserDto) {
    localStorage.setItem(this.userDtoKey, JSON.stringify(userDto));
    await this.userService.updateUser(userDto)
  }

  async addOwnedGroup(group: string) {
    let groups = await this.getOwnedGroups();
    groups.push(group);
    localStorage.setItem(this.ownedGroupsKey, JSON.stringify(groups));
    this.userService.createGroup(group)
  }

}
