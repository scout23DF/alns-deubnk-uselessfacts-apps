import { Injectable } from '@angular/core';
import {HttpClient} from '@angular/common/http';
import {Observable} from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class ManagementService {

  private endpointBaseUrl = 'http://localhost:8080'; // URL base da API

  constructor(private http: HttpClient) {}

  deleteAllFacts(): Observable<void> {
    return this.http.delete<void>(`${this.endpointBaseUrl}/api/management`);
  }

}
