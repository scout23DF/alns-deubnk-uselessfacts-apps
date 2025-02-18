import {Injectable} from '@angular/core';
import {MatDialog} from '@angular/material/dialog';
import {AlertDTO} from './utils.models';
import {CommonDialogComponent} from './common-dialog.component';

@Injectable({
  providedIn: 'root',
})
export class AlertService {

  constructor(
    private matDialog: MatDialog
  ) {}

  public openSimpleDialogWithMessage(alertDetailsDTO: AlertDTO): void {
    this.matDialog.open(CommonDialogComponent, {
      data: alertDetailsDTO
    });
  }

}
