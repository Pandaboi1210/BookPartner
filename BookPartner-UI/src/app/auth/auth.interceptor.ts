import { HttpInterceptorFn } from '@angular/common/http';
import { inject } from '@angular/core';
import { AuthService } from './auth.service';

export const authInterceptor: HttpInterceptorFn = (req, next) => {
  const authService = inject(AuthService);
  const header = authService.getAuthHeader();

  const isApiRequest = req.url.startsWith('http://localhost:8082/api/v1');
  if (!header || !isApiRequest) {
    return next(req);
  }

  return next(
    req.clone({
      setHeaders: {
        Authorization: header
      }
    })
  );
};

