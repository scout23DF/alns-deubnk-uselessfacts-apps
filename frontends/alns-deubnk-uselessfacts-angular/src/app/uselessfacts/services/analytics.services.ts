import { Injectable } from '@angular/core';
import {HttpClient} from '@angular/common/http';
import {catchError, Observable} from 'rxjs';
import {AnalyticsFactsSummaryResponse} from '../models/analytics.models';
import {ApplicationConfigService} from '../../core/config/application-config.service';

@Injectable({
  providedIn: 'root'
})
export class AnalyticsService {

  private endpointBaseUrl = ApplicationConfigService.BACKEND_REST_API_ENDPOINT_BASE_URL;

  constructor(private http: HttpClient) {}

  public getAnalyticsFactsStatistics(): Observable<AnalyticsFactsSummaryResponse> {
    return this.http.get<AnalyticsFactsSummaryResponse>(`${this.endpointBaseUrl}/api/admin/statistics`)
      .pipe(catchError(error => {
        console.error('Error fetching analytics facts statistics:', error);
        throw error;
      }));
  }

}
