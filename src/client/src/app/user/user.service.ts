import {Injectable} from '@angular/core';
import {HttpClient} from "@angular/common/http";
import {UserDto} from "./user.dto";

@Injectable()

export class UserService {

  constructor(private http: HttpClient) {
  }


  getUser(username: string): Promise<UserDto> {
    return this.http.get<UserDto>('http://localhost:8090/api/user/' + username
    ).toPromise();
  }

  editUser(user: UserDto): Promise<Object> {
    return this.http.post('http://localhost:8090/api/user/', user).toPromise();
  }

}
