import { Component } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Router, RouterLink } from '@angular/router';

@Component({
  selector: 'app-home',
  standalone: true,
  imports: [RouterLink],
  templateUrl: './home.html',
  styleUrl: './home.css'
})
export class HomeComponent {
  errorMessage = '';

  users = [
    {
      username: 'soumya',
      displayName: 'Soumya',
      initials: 'SO',
      role: 'Authors & Publishers',
      avatarColor: 'linear-gradient(135deg, #6366f1, #8b5cf6)',
      tagColor: '#a78bfa',
      tagBg: 'rgba(139,92,246,0.15)',
      tag: 'SOUMYA'
    },
    {
      username: 'hema',
      displayName: 'Hema',
      initials: 'HE',
      role: 'Titles & Title-Authors',
      avatarColor: 'linear-gradient(135deg, #0ea5e9, #38bdf8)',
      tagColor: '#38bdf8',
      tagBg: 'rgba(56,189,248,0.15)',
      tag: 'HEMA'
    },
    {
      username: 'harini',
      displayName: 'Harini',
      initials: 'HA',
      role: 'Stores & Sales',
      avatarColor: 'linear-gradient(135deg, #10b981, #34d399)',
      tagColor: '#34d399',
      tagBg: 'rgba(52,211,153,0.15)',
      tag: 'HARINI'
    },
    {
      username: 'pradeep',
      displayName: 'Pradeep',
      initials: 'PR',
      role: 'Royalties & Discounts',
      avatarColor: 'linear-gradient(135deg, #f59e0b, #fbbf24)',
      tagColor: '#fbbf24',
      tagBg: 'rgba(251,191,36,0.15)',
      tag: 'PRADEEP'
    },
    {
      username: 'subasri',
      displayName: 'Subasri',
      initials: 'SU',
      role: 'Jobs & Employees',
      avatarColor: 'linear-gradient(135deg, #ef4444, #f87171)',
      tagColor: '#f87171',
      tagBg: 'rgba(248,113,113,0.15)',
      tag: 'SUBASRI'
    }
  ];

  constructor(
    private router: Router,
    private route: ActivatedRoute
  ) {
    this.route.queryParamMap.subscribe((params) => {
      this.errorMessage = params.get('error') === 'wrong-login'
        ? 'Wrong login. Please use the correct account to access that page.'
        : '';
    });
  }

  goToLogin(username: string) {
    this.router.navigate(['/login'], { queryParams: { user: username } });
  }
}
