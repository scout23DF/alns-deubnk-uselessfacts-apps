import { Injectable } from '@angular/core';

@Injectable({
  providedIn: 'root'
})
export class AuthService {
  private userRole: string | null = null; // Armazena a role do usuário

  constructor() {}

  // Simulação de login
  login(role: string) {
    this.userRole = role;
  }

  // Verifica se o usuário tem a role "ROLE_ADMIN"
  isAdmin(): boolean {
    return this.userRole === 'ROLE_ADMIN';
  }

  // Simulação de logout
  logout() {
    this.userRole = null;
  }
}
