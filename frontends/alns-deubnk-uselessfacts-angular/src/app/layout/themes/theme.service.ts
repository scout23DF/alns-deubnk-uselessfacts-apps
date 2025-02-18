import { Injectable } from '@angular/core';

@Injectable({
  providedIn: 'root'
})
export class ThemeService {
  private themes = ['light-theme', 'dark-theme', 'colorful-theme', 'monochrome-theme'];
  private currentTheme = 'light-theme';

  constructor() {
    this.loadTheme();
  }

  loadTheme() {
    const savedTheme = localStorage.getItem('theme');
    if (savedTheme && this.themes.includes(savedTheme)) {
      this.setTheme(savedTheme);
    } else {
      this.setTheme(this.currentTheme);
    }
  }

  getThemes() {
    return this.themes;
  }

  setTheme(theme: string) {
    this.currentTheme = theme;
    document.body.className = theme;
    localStorage.setItem('theme', theme);
  }

  toggleTheme() {
    const currentIndex = this.themes.indexOf(this.currentTheme);
    const nextIndex = (currentIndex + 1) % this.themes.length;
    this.setTheme(this.themes[nextIndex]);
  }

}
