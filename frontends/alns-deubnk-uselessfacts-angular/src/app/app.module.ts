import {NgModule} from '@angular/core';
import {BrowserModule} from '@angular/platform-browser';
import {AppRoutingModule} from './app.routes';
import {AppComponent} from './app.component';
import {BrowserAnimationsModule} from '@angular/platform-browser/animations';
import {MatListModule} from '@angular/material/list';
import {MatToolbarModule} from '@angular/material/toolbar';
import {MatSidenavModule} from '@angular/material/sidenav';
import {SidebarComponent} from './layout/sidebar/sidebar.component';
import {HeaderComponent} from './layout/header/header.component';
import {FooterComponent} from './layout/footer/footer.component';
import {FactsDashboardComponent} from './uselessfacts/components/public/facts-dashboard.component';
import {AnalyticsDashboardComponent} from './uselessfacts/components/private/analytics-dashboard.component';
import {HTTP_INTERCEPTORS, HttpClientModule} from '@angular/common/http';
import {ApiKeyAuthInterceptor} from './core/interceptors/apikey-auth.interceptor';
import {ErrorHandlerInterceptor} from './core/interceptors/error-handler.interceptor';

@NgModule({
  imports: [
    BrowserModule,
    HttpClientModule,
    AppRoutingModule,
    BrowserAnimationsModule,
    MatListModule,
    MatToolbarModule,
    MatSidenavModule,
    AppComponent,
    SidebarComponent,
    FooterComponent,
    HeaderComponent,
    FactsDashboardComponent,
    AnalyticsDashboardComponent
  ],
  providers: [
    {
      provide: HTTP_INTERCEPTORS,
      useClass: ApiKeyAuthInterceptor,
      multi: true
    },
    {
      provide: HTTP_INTERCEPTORS,
      useClass: ErrorHandlerInterceptor,
      multi: true
    }
  ],
  declarations: [

  ],
  bootstrap: [AppComponent]
})
export class AppModule {}
