import {platformBrowserDynamic} from '@angular/platform-browser-dynamic';
import {AppModule} from './app/app.module';

// bootstrapApplication(AppComponent, appConfig)
//   .catch((err) => console.error(err));

platformBrowserDynamic()
  .bootstrapModule(AppModule, { preserveWhitespaces: true })
  // eslint-disable-next-line no-console
  .then(() => console.log('Application started'))
  .catch(err => console.error(err));
