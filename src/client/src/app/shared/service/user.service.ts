import {Injectable} from '@angular/core';
import {HttpClient} from "@angular/common/http";
import {UserDto} from "../dto/user.dto";
import {MemberGroupDto} from "../dto/member-group.dto";
import {GroupDto} from "../dto/group.dto";

@Injectable()

export class UserService {

  constructor(private http: HttpClient) {
  }


  getUser(username: string): Promise<UserDto> {
    return this.http.get<UserDto>('http://localhost:8090/api/user/' + username)
      .toPromise();
  }

  getDrawUser(groupName: string): Promise<UserDto> {
    return this.http.get<UserDto>('http://localhost:8090/api/user/draw/' + groupName)
      .toPromise();
  }

  editUser(user: UserDto): Promise<Object> {
    return this.http.post('http://localhost:8090/api/user/edit', user).toPromise();
  }

  getMemberGroups(): Promise<MemberGroupDto[]> {
    return this.http.get<MemberGroupDto[]>('http://localhost:8090/api/user/groups/member')
      .toPromise();
  }

  getOwnerGroups(): Promise<string[]> {
    return this.http.get<string[]>('http://localhost:8090/api/user/groups/owner')
      .toPromise();
  }

  sendInvitationRequestToGroup(groupName: string): Promise<boolean> {
    return this.http.get<boolean>('http://localhost:8090/api/user/join/' + groupName)
      .toPromise();
  }

  createGroup(name: string): Promise<boolean> {
    return this.http.post<boolean>('http://localhost:8090/api/user/create/' + name, {}).toPromise();
  }

  groupExists(groupName: string): Promise<boolean> {
    return this.http.get<boolean>('http://localhost:8090/api/group/exists/' + groupName)
      .toPromise();
  }

  requestGroup(name: string): Promise<object> {
    return this.http.post('http://localhost:8090/api/user/requestGroup/' + name, {}).toPromise();
  }

  getGroup(name: string): Promise<GroupDto> {
    return this.http.get<GroupDto>('http://localhost:8090/api/group/' + name).toPromise();
  }

  getRequests(groupName: string): Promise<UserDto[]> {
    return this.http.get<UserDto[]>('http://localhost:8090/api/group/requests/' + groupName).toPromise();
  }

  acceptRequest(username: string, groupName: string): Promise<object> {
    return this.http.post(
      'http://localhost:8090/api/group/accept/' + groupName + '/' + username, {}).toPromise();
  }

}
