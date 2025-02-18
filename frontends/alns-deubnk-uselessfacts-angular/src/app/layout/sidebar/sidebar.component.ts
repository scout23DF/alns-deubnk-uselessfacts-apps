import { Component } from '@angular/core';
import {MatSidenav, MatSidenavContainer} from '@angular/material/sidenav';
import {MatListItem, MatNavList} from '@angular/material/list';
import {RouterLink} from '@angular/router';

@Component({
  selector: 'app-sidebar',
  templateUrl: './sidebar.component.html',
  imports: [
    MatSidenavContainer,
    MatSidenav,
    MatNavList,
    MatListItem,
    RouterLink
  ],
  styleUrls: ['./sidebar.component.scss']
})
export class SidebarComponent {
  isExpanded = true;

  toggleSidebar() {
    this.isExpanded = !this.isExpanded;
  }
}
