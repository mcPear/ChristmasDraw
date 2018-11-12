import {Injectable} from '@angular/core';
import {HttpClient} from "@angular/common/http";
import {UserDto} from "../dto/user.dto";
import {MemberGroupDto} from "../dto/member-group.dto";
import {GroupSimpleDto} from "../dto/group_simple";
import {UserIncludeDto} from "../dto/user_include.dto";
import {GiftPartDto} from "../dto/gift-part.dto";

@Injectable()

export class UserService {
  BASE_URL = 'http://localhost:8090/api/';
  GROUP_URL = this.BASE_URL + 'group/';
  USER_URL = this.BASE_URL + 'user/';
  MEMBERSHIP_URL = this.BASE_URL + 'membership/';
  GIFT_PART_URL = this.BASE_URL + 'giftPart/';

  constructor(private http: HttpClient) {
  }

  getUser(username: string): Promise<UserDto> {
    return this.http.get<UserDto>(this.USER_URL + username)
      .toPromise();
  }

  getDrawUser(groupName: string): Promise<UserDto> {
    return this.http.get<UserDto>(this.MEMBERSHIP_URL + 'drawUser/' + groupName)
      .toPromise();
  }

  getDrawUserOfVirtual(groupName: string, username: string): Promise<UserDto> {
    return this.http.get<UserDto>(this.MEMBERSHIP_URL + 'drawUserOfVirtual/' + groupName + "/" + username)
      .toPromise();
  }

  updateUser(user: UserDto): Promise<Object> {
    return this.http.post(this.USER_URL + 'update', user).toPromise();
  }

  getJoinedGroups(): Promise<MemberGroupDto[]> {
    return this.http.get<MemberGroupDto[]>(this.MEMBERSHIP_URL + 'groups/member')
      .toPromise();
  }

  getOwnedGroups(): Promise<string[]> {
    return this.http.get<string[]>(this.MEMBERSHIP_URL + 'groups/owner')
      .toPromise();
  }

  createGroup(name: string): Promise<boolean> {
    return this.http.post<boolean>(this.GROUP_URL + 'create/' + name, {}).toPromise();
  }

  existsGroup(groupName: string): Promise<boolean> {
    return this.http.get<boolean>(this.GROUP_URL + 'exists/' + groupName)
      .toPromise();
  }

  requestGroup(name: string): Promise<object> {
    return this.http.post(this.MEMBERSHIP_URL + 'requestGroup/' + name, {}).toPromise();
  }

  getGroup(name: string): Promise<GroupSimpleDto> {
    return this.http.get<GroupSimpleDto>(this.GROUP_URL + name).toPromise();
  }

  getGroupRequests(groupName: string): Promise<UserDto[]> {
    return this.http.get<UserDto[]>(this.MEMBERSHIP_URL + 'requests/' + groupName).toPromise();
  }

  setIsAcceptedRequest(username: string, groupName: string, value: boolean): Promise<object> {
    return this.http.post(
      this.MEMBERSHIP_URL + 'accept/' + groupName + '/' + username + '/' + value, {}).toPromise();
  }

  performDraw(groupName: string, members: UserIncludeDto[]): Promise<object> {
    return this.http.post(this.MEMBERSHIP_URL + 'draw/' + groupName, members).toPromise();
  }

  getGroupMembers(groupName: string): Promise<UserIncludeDto[]> {
    return this.http.get<UserIncludeDto[]>(this.MEMBERSHIP_URL + 'members/' + groupName).toPromise();
  }

  getAllGroups(): Promise<GroupSimpleDto[]> {
    return this.http.get<GroupSimpleDto[]>(this.GROUP_URL + 'getAll').toPromise();
  }

  deleteGroup(groupName: string): Promise<object> {
    return this.http.delete(this.GROUP_URL + groupName).toPromise();
  }

  updateGroup(group: GroupSimpleDto): Promise<object> {
    return this.http.post(this.GROUP_URL + 'update', group).toPromise();
  }

  getAllUsers(): Promise<UserDto[]> {
    return this.http.get<UserDto[]>(this.USER_URL + 'getAll').toPromise();
  }

  deleteUser(username: string): Promise<object> {
    return this.http.delete(this.USER_URL + username).toPromise();
  }

  updateIncludeMembers(members: UserIncludeDto[], groupName: string): Promise<object> {
    return this.http.post(this.MEMBERSHIP_URL + 'updateIncludeMembers/' + groupName, members).toPromise();
  }

  deleteMembership(group: MemberGroupDto) {
    return this.http.delete(this.MEMBERSHIP_URL + group.groupName).toPromise();
  }

  addMember(groupName: string, user: UserDto) {
    return this.http.post(this.MEMBERSHIP_URL + 'addMemberByHand/' + groupName, user).toPromise();
  }

  isVirtual(username: string): Promise<boolean> {
    return this.http.get<boolean>(this.USER_URL + 'virtual/' + username)
      .toPromise();
  }

  getAllGiftParts(groupname: string): Promise<GiftPartDto[]> {
    return this.http.get<GiftPartDto[]>(this.GIFT_PART_URL + groupname).toPromise();
  }

  updateGiftParts(dtos: GiftPartDto[], groupName: string): Promise<object> {
    return this.http.post(this.GIFT_PART_URL + "/" + groupName, dtos).toPromise();
  }

  sendMails(groupName: string) {
    return this.http.post(this.MEMBERSHIP_URL + 'sendMails/' + groupName, {}).toPromise();
  }

}
