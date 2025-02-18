import { Injectable } from '@angular/core';
import {HttpClient} from '@angular/common/http';
import {catchError, map, Observable} from 'rxjs';
import {UselessFactResponse} from '../models/fact.models';
import {ApplicationConfigService} from '../../core/config/application-config.service';

@Injectable({
  providedIn: 'root'
})
export class UselessFactsService {

  private endpointBaseUrl = ApplicationConfigService.BACKEND_REST_API_ENDPOINT_BASE_URL;

  constructor(private http: HttpClient) {}

  public fetchFromRemoteAndCreate(): Observable<UselessFactResponse> {

    return this.http
      .post<UselessFactResponse>(this.endpointBaseUrl + "/api/facts", null, { observe: 'response' })
      .pipe(map((response) => response.body as UselessFactResponse));

  }

  public getAllFacts(): Observable<UselessFactResponse[]> {
    return this.http.get<UselessFactResponse[]>(`${this.endpointBaseUrl}/api/facts`);
  }

  public getFactByShortenedUrl(shortenedUrl: string): Observable<UselessFactResponse> {
    return this.http.get<UselessFactResponse>(`${this.endpointBaseUrl}/api/facts/${shortenedUrl}`);
  }

}
