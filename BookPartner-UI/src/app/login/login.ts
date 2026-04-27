import { Component } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { ActivatedRoute, Router } from '@angular/router';
import { AuthService } from '../auth/auth.service';

@Component({
  selector: 'app-login',
  standalone: true,
  imports: [FormsModule],
  templateUrl: './login.html',
  styleUrls: ['./login.css']
})
export class LoginComponent {

  username: string = '';
  password: string = '';

  constructor(
    private router: Router,
    private route: ActivatedRoute,
    private authService: AuthService
  ) {
    const prefilledUser = this.route.snapshot.queryParamMap.get('user');
    if (prefilledUser) {
      this.username = prefilledUser;
    }
  }

  onLogin() {
    const result = this.authService.login(this.username, this.password);
    if (!result.ok) {
      alert('Invalid username or password');
      return;
    }

    if (result.role === 'ADMIN') {
      alert('Logged in as Admin. You can test all endpoints.');
    }

    this.router.navigate([result.route ?? '/']);
  }

  goBack() {
    this.router.navigate(['/']);
  }

}