import { Injectable } from '@angular/core';
import { HttpInterceptor, HttpRequest, HttpHandler, HttpEvent } from '@angular/common/http';
import { Observable } from 'rxjs';
import {ApplicationConfigService} from '../config/application-config.service';

@Injectable()
export class ApiKeyAuthInterceptor implements HttpInterceptor {
  private apiKey: string = ApplicationConfigService.CONFIG_API_KEY_FOR_ADMINS;

  intercept(req: HttpRequest<any>, next: HttpHandler): Observable<HttpEvent<any>> {

    if (req.url.includes("/api/admin/")) {

      const clonedRequest = req.clone({
        setHeaders: {
          'X-API-KEY': this.apiKey
        }
      });
      return next.handle(clonedRequest);
    }

    return next.handle(req);
  }
}
