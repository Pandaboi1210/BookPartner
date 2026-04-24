import { Component } from '@angular/core';
import { Router } from '@angular/router';
import { AuthService } from '../auth/auth.service';

@Component({
  selector: 'app-admin',
  standalone: true,
  templateUrl: './admin.component.html'
})
export class AdminComponent {
  constructor(
    private router: Router,
    private authService: AuthService
  ) {}

  openModule(target: 'pradeep' | 'hema' | 'soumya' | 'harini' | 'subasri'): void {
    this.router.navigate([`/${target}`]);
  }

  logout(): void {
    this.authService.logout();
    this.router.navigate(['/']);
  }
}

