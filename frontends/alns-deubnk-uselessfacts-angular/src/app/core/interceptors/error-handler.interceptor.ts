import {Injectable} from '@angular/core';
import {HttpErrorResponse, HttpEvent, HttpHandler, HttpInterceptor, HttpRequest} from '@angular/common/http';
import {Observable, throwError} from 'rxjs';
import {catchError} from 'rxjs/operators';
import {AlertDTOBuilder} from '../utils/utils.models';
import {AlertService} from '../utils/alert.service';
import {Router} from '@angular/router';


@Injectable()
export class ErrorHandlerInterceptor implements HttpInterceptor {
  constructor(
    private alertService: AlertService,
    private router: Router,
  ) {}

  intercept(request: HttpRequest<any>, next: HttpHandler): Observable<HttpEvent<any>> {
    return next.handle(request).pipe(

      catchError((httpErrorResponse: HttpErrorResponse) => {
        let errorMessage = 'Unknown error!';
        if (httpErrorResponse.error instanceof ErrorEvent) {
          // Error on Client side
          errorMessage = `Error: ${httpErrorResponse.error.message}`;
        } else {
          // Error on Server side
          errorMessage = `Error Code: ${httpErrorResponse.status}\nMessage: ${httpErrorResponse.message}`;
        }
        const alertDetailsDTO = new AlertDTOBuilder()
          .setTitle('Unexpected Error')
          .setMessage(errorMessage)
          .setType('danger')
          .setLabelButton1('OK')
          .setHttpErrorResponse(httpErrorResponse)
          .build();

        this.alertService.openSimpleDialogWithMessage(alertDetailsDTO);

        if (httpErrorResponse.status === 401 || httpErrorResponse.status === 403) {
          this.router.navigate(['']).then(r => console.log('Redirected to Home page'));
        }

        return throwError(errorMessage);

      })

    );

  }
}
