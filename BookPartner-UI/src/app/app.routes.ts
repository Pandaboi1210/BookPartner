import { Routes } from '@angular/router';
import { HariniComponent } from './harini/harini'; 

export const routes: Routes = [
  { path: '', component: HariniComponent },
  { path: '**', redirectTo: '' }
];