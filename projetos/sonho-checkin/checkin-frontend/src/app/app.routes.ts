
import { Routes } from '@angular/router';
import { ReservasComponent } from './reservas/reservas.component';
import { CheckinPublicComponent } from './checkin-public/checkin-public.component';

export const routes: Routes = [
  { path: '', pathMatch: 'full', redirectTo: 'reservas' },
  { path: 'reservas', component: ReservasComponent },
  { path: 'checkin/:token', component: CheckinPublicComponent }
];
