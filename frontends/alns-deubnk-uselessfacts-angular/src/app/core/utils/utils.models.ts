import {HttpErrorResponse} from '@angular/common/http';

export type AlertType = 'success' | 'danger' | 'warning' | 'info';

export interface IAlertDTO {
  id?: string;
  title?: string;
  type?: AlertType;
  message?: string;
  labelButton1?: string;
  httpErrorResponse?: HttpErrorResponse
}

export class AlertDTO implements IAlertDTO {
  constructor(public id?: string,
              public title?: string,
              public type?: AlertType,
              public message?: string,
              public labelButton1?: string,
              public httpErrorResponse?: HttpErrorResponse) {
  }
}

export class AlertDTOBuilder {
  private id?: string;
  private title?: string;
  private type?: AlertType;
  private message?: string;
  private labelButton1?: string;
  private httpErrorResponse?: HttpErrorResponse;

  public setId(id: string): AlertDTOBuilder {
    this.id = id;
    return this;
  }

  public setTitle(title: string): AlertDTOBuilder {
    this.title = title;
    return this;
  }

  public setType(type: AlertType): AlertDTOBuilder {
    this.type = type;
    return this;
  }

  public setMessage(message: string): AlertDTOBuilder {
    this.message = message;
    return this;
  }

  public setLabelButton1(labelButton1: string): AlertDTOBuilder {
    this.labelButton1 = labelButton1;
    return this;
  }

  public setHttpErrorResponse(httpErrorResponse?: HttpErrorResponse): AlertDTOBuilder {
    this.httpErrorResponse = httpErrorResponse;
    return this;
  }

  public build(): AlertDTO {
    if (!this.title || !this.message) {
      throw new Error("Missing required fields to build AlertDTO.");
    }
    return new AlertDTO(
      this.id,
      this.title,
      this.type,
      this.message,
      this.labelButton1,
      this.httpErrorResponse
    );
  }
}
