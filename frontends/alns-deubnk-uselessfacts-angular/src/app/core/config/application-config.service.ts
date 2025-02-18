import {Injectable} from '@angular/core';

@Injectable({
  providedIn: 'root',
})
export class ApplicationConfigService {

  public static BACKEND_REST_API_ENDPOINT_BASE_URL : string = "http://localhost:8080";
  public static CONFIG_API_KEY_FOR_ADMINS: string = "SomeMegaSecretAPI-KEY>>>w3bRTWuk61OxmCfYXYVjXdSyKYTZQBtLvRZCpixwcJKPp7VUQA5wpTlloyhPAFWdz1jQkNbvZY5wBVZZmBPVzF54a1gZmpJ8678Ewucv";

}
