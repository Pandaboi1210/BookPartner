import { Routes } from '@angular/router';
import { HomeComponent } from './home/home';
import { LoginComponent } from './login/login';
import { PradeepComponent } from './pradeep/pradeep';
import { HemaHamsaveniComponent } from './hema-hamsaveni/hema-hamsaveni.component';
import { AdminComponent } from './admin/admin.component';
import { SoumyaComponent } from './soumya/soumya.component';
import { HariniComponent } from './harini/harini';
import { SubasriComponent } from './subasri/subasri';
import { roleGuard } from './auth/role.guard';
 

export const routes: Routes = [

  {
    path: '',
    component: HomeComponent
  },

  {
    path: 'login',
    component: LoginComponent
  },

  {
    path: 'pradeep',
    component: PradeepComponent,
    canActivate: [roleGuard],
    data: { requiredRole: 'PRADEEP' }
  },

  {
    path: 'hema',
    component: HemaHamsaveniComponent,
    canActivate: [roleGuard],
    data: { requiredRole: 'HEMA' }
  },

  {
    path: 'admin',
    component: AdminComponent,
    canActivate: [roleGuard],
    data: { requiredRole: 'ADMIN' }
  },

  {
    path: 'soumya',
    component: SoumyaComponent,
    canActivate: [roleGuard],
    data: { requiredRole: 'SOUMYA' }
  },

  {
    path: 'harini',
    component: HariniComponent,
    canActivate: [roleGuard],
    data: { requiredRole: 'HARINI' }
  },

  {
    path: 'subasri',
    component: SubasriComponent,
    canActivate: [roleGuard],
    data: { requiredRole: 'SUBASRI' }
  }

];