import {Component, Inject} from '@angular/core';
import {
  MAT_DIALOG_DATA,
  MatDialogActions,
  MatDialogContent,
  MatDialogRef,
  MatDialogTitle
} from '@angular/material/dialog';
import {MatButton} from '@angular/material/button';
import {AlertDTO} from './utils.models';

@Component({
  selector: 'app-common-dialog',
  imports: [
    MatDialogTitle,
    MatDialogContent,
    MatDialogActions,
    MatButton
  ],
  template: `
    <h1 mat-dialog-title>{{ alertDetailsDTO.title }}</h1>
    <div mat-dialog-content>
      <p>{{ alertDetailsDTO.message }}</p>
    </div>
    <div mat-dialog-actions>
      <button mat-button (click)="onClose()">{{ alertDetailsDTO.labelButton1 }}</button>
    </div>
  `
})
export class CommonDialogComponent {
  constructor(
    public dialogRef: MatDialogRef<CommonDialogComponent>,
    @Inject(MAT_DIALOG_DATA)
    public alertDetailsDTO: AlertDTO
  ) {}

  onClose(): void {
    this.dialogRef.close();
  }
}
