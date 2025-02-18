import {Component, inject} from '@angular/core';
import {MatToolbar} from '@angular/material/toolbar';
import {NgIf} from '@angular/common';
import {AuthService} from '../../core/auth/auth.services';
import {MatButton} from '@angular/material/button';
import {Router, RouterLink} from '@angular/router';
import {MatMenu, MatMenuItem, MatMenuTrigger} from '@angular/material/menu';
import {ThemeService} from '../themes/theme.service';

@Component({
  selector: 'app-header',
  templateUrl: './header.component.html',
  imports: [
    MatToolbar,
    NgIf,
    MatButton,
    MatMenuTrigger,
    MatMenu,
    MatMenuItem,
    RouterLink
  ],
  styleUrls: ['./header.component.scss']
})
export class HeaderComponent {
  constructor(private authService: AuthService,
              private themeService: ThemeService) {

    this.availableThemesList = this.themeService.getThemes();
  }
  private router = inject(Router);
  availableThemesList: string[] = [];


  isAdmin() {
    return this.authService.isAdmin();
  }

  // Simulação de login como ROLE_ADMIN
  loginAsAdmin() {
    this.authService.login('ROLE_ADMIN');
  }

  // Simulação de logout
  logout() {
    this.authService.logout();
    this.router.navigateByUrl("").then(r => console.log("Logout"));
  }

  toggleTheme() {
    this.themeService.toggleTheme();
  }

  setTheme(theme: string) {
    this.themeService.setTheme(theme);
  }

}
