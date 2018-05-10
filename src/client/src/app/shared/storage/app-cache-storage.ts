import {MemberGroupDto} from "../dto/member-group.dto";
import {UserDto} from "../dto/user.dto";
import {Injectable} from "@angular/core";
import {HttpClient} from "@angular/common/http";
import {UserService} from "../service/user.service";

@Injectable()

export class AppCacheStorage {
  groupsWhereMemberPrefix = 'groupsWhereMember';
  groupsWhereOwnerPrefix = 'groupsWhereOwner';
  userDataPrefix = 'userData';
  groupsWhereMemberKey: string;
  groupsWhereOwnerKey: string;
  userDataKey: string;
  username: string;

  constructor(private userService: UserService) {
  }

  initForUser(username: string) {
    console.log('cache init started')
    this.groupsWhereMemberKey = this.groupsWhereMemberPrefix + '-' + username;
    this.groupsWhereOwnerKey = this.groupsWhereOwnerPrefix + '-' + username;
    this.userDataKey = this.userDataPrefix + '-' + username;
    this.username = username;
    console.log('cache init done')
  }

  async getGroupsWhereOwner(): Promise<string[]> {
    let cachedGroups = JSON.parse(localStorage.getItem(this.groupsWhereOwnerKey))
    if (cachedGroups === null) {
      return this.fetchGroupsWhereOwner()
    } else {
      return cachedGroups
    }
  }

  private async fetchGroupsWhereOwner(): Promise<string[]> {
    console.log(this.groupsWhereOwnerKey)
    let serviceGroups = await this.userService.getOwnerGroups()
    localStorage.setItem(this.groupsWhereOwnerKey, JSON.stringify(serviceGroups as string[]))
    return serviceGroups
  }

  // async getGroupsWhereMember(): Promise<MemberGroupDto[]> {
  //   let cachedGroups = JSON.parse(localStorage.getItem(this.groupsWhereMemberKey))
  //   if (cachedGroups === null) {
  //     return this.fetchGroupsWhereMember()
  //   } else {
  //     return cachedGroups
  //   }
  // }

  // private async fetchGroupsWhereMember(): Promise<MemberGroupDto[]> {
  //   let serviceGroups = await this.userService.getMemberGroups()
  //   localStorage.setItem(this.groupsWhereMemberKey, JSON.stringify(serviceGroups as MemberGroupDto[]))
  //   return serviceGroups
  // }

  getUserDto(): Promise<UserDto> {
    let cachedUserDto = JSON.parse(localStorage.getItem(this.userDataKey))
    if (cachedUserDto === null) {
      return this.fetchUserDto()
    } else {
      return cachedUserDto
    }
  }

  private async fetchUserDto(): Promise<UserDto> {
    console.log('fetchUser started')
    let userDto = await this.userService.getUser(this.username)
    localStorage.setItem(this.userDataKey, JSON.stringify(userDto as UserDto))
    return userDto
    console.log('fetchUser done')
  }


  async setUserDto(userDto: UserDto) {
    localStorage.setItem(this.userDataKey, JSON.stringify(userDto))
    await this.userService.updateUser(userDto)
  }

  // async addGroupWhereMember(group: string) {
  //   let groups = await this.getGroupsWhereMember()
  //   groups.push({groupName: group, requestAccepted: false} as MemberGroupDto)
  //   localStorage.setItem(this.groupsWhereMemberKey, JSON.stringify(groups))
  //   this.userService.requestGroup(group)
  // }

  async addGroupWhereOwner(group: string) {
    let groups = await this.getGroupsWhereOwner()
    groups.push(group)
    localStorage.setItem(this.groupsWhereOwnerKey, JSON.stringify(groups))
    this.userService.createGroup(group)
  }

}
