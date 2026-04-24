import { Injectable } from '@angular/core';

type UserRole = 'SOUMYA' | 'HEMA' | 'HARINI' | 'PRADEEP' | 'SUBASRI' | 'ADMIN';

interface UserConfig {
  password: string;
  role: UserRole;
  route: string;
}

const USERS: Record<string, UserConfig> = {
  soumya: { password: 'soumya123', role: 'SOUMYA', route: '/soumya' },
  hema: { password: 'hema123', role: 'HEMA', route: '/hema' },
  harini: { password: 'harini123', role: 'HARINI', route: '/harini' },
  pradeep: { password: 'pradeep123', role: 'PRADEEP', route: '/pradeep' },
  subasri: { password: 'subasri123', role: 'SUBASRI', route: '/subasri' },
  admin: { password: 'admin123', role: 'ADMIN', route: '/admin' }
};

@Injectable({
  providedIn: 'root'
})
export class AuthService {
  private readonly storageKey = 'bookPartnerAuth';

  login(username: string, password: string): { ok: boolean; route?: string; role?: UserRole } {
    const normalizedUser = username.trim().toLowerCase();
    const user = USERS[normalizedUser];
    if (!user || user.password !== password) {
      return { ok: false };
    }

    const token = btoa(`${normalizedUser}:${password}`);
    sessionStorage.setItem(
      this.storageKey,
      JSON.stringify({
        username: normalizedUser,
        role: user.role,
        token
      })
    );

    return { ok: true, route: user.route, role: user.role };
  }

  getAuthHeader(): string | null {
    const raw = sessionStorage.getItem(this.storageKey);
    if (!raw) return null;

    try {
      const parsed = JSON.parse(raw) as { token?: string };
      return parsed.token ? `Basic ${parsed.token}` : null;
    } catch {
      return null;
    }
  }

  getCurrentRole(): UserRole | null {
    const raw = sessionStorage.getItem(this.storageKey);
    if (!raw) return null;

    try {
      const parsed = JSON.parse(raw) as { role?: UserRole };
      return parsed.role ?? null;
    } catch {
      return null;
    }
  }

  logout(): void {
    sessionStorage.removeItem(this.storageKey);
  }
}

