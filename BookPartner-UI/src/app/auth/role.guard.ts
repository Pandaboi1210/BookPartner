import { inject } from '@angular/core';
import { CanActivateFn, Router } from '@angular/router';
import { AuthService } from './auth.service';

export const roleGuard: CanActivateFn = (route) => {
  const authService = inject(AuthService);
  const router = inject(Router);

  const requiredRole = route.data?.['requiredRole'] as string | undefined;
  const currentRole = authService.getCurrentRole();

  // Admin can open every protected module page.
  if (currentRole === 'ADMIN') {
    return true;
  }

  if (requiredRole && currentRole === requiredRole) {
    return true;
  }

  return router.createUrlTree([''], {
    queryParams: { error: 'wrong-login' }
  });
};

