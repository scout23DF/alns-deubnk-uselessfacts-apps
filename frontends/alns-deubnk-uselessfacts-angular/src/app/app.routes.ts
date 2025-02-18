import {NgModule} from '@angular/core';
import {RouterModule, Routes} from '@angular/router';
import {FactsDashboardComponent} from './uselessfacts/components/public/facts-dashboard.component';
import {AnalyticsDashboardComponent} from './uselessfacts/components/private/analytics-dashboard.component';
import {FakeAuthGuard} from './core/auth/fake-auth.guard';

export const routes: Routes = [
  { path: '', component: FactsDashboardComponent },
  { path: 'public/facts-dashboard', component: FactsDashboardComponent },
  { path: 'private/analytics', component: AnalyticsDashboardComponent, canActivate: [FakeAuthGuard] }
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule {}
