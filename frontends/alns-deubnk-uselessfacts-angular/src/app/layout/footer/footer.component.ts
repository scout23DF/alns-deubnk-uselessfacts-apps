import { Component } from '@angular/core';
import {MatToolbar} from '@angular/material/toolbar';

@Component({
  selector: 'app-footer',
  templateUrl: './footer.component.html',
  imports: [
    MatToolbar
  ],
  styleUrls: ['./footer.component.scss']
})
export class FooterComponent {}
