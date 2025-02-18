import {Injectable} from '@angular/core';
import {ActivatedRouteSnapshot, CanActivate, Router, RouterStateSnapshot} from '@angular/router';
import {AuthService} from './auth.services';
import {AlertDTO} from '../utils/utils.models';
import {AlertService} from '../utils/alert.service';

@Injectable({
  providedIn: 'root'
})
export class FakeAuthGuard implements CanActivate {

  constructor(
    private authService: AuthService,
    private router: Router,
    private alertService: AlertService
  ) {}

  canActivate(
    route: ActivatedRouteSnapshot,
    state: RouterStateSnapshot): boolean {

    const isAuthenticated = this.authService.isAdmin();

    if (!isAuthenticated) {
      const alertDetailsDTO: AlertDTO = new AlertDTO(
        '1', // id
        'Authorization Error',
        'danger',
        'Only Administrators are allowed to access this route.', // message
        'Redirect to Home'
      );
      this.alertService.openSimpleDialogWithMessage(alertDetailsDTO);
      this.router.navigate(['']).then(r => console.log('Redirected to Home page'));
      return false;
    }

    return true;
  }

}
