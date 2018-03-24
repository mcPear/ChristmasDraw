import {Injectable} from '@angular/core';
import {HttpClient, HttpHeaders} from "@angular/common/http";

@Injectable()
export class AppService {

  constructor(private http: HttpClient) {
  }

  fetchUser(): Promise<String> {
    return this.http.get<String>('http://localhost:8090/api/user/fetch', {
        headers: new HttpHeaders({'Content-Type': 'application/json'}),
        responseType: "text" as "json"
      }
    ).toPromise();
  }

}
