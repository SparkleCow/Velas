import { Injectable } from '@angular/core';
import {
  HttpInterceptor,
  HttpRequest,
  HttpHandler,
  HttpEvent
} from '@angular/common/http';
import { Observable } from 'rxjs';

@Injectable()
export class AuthInterceptor implements HttpInterceptor {

  intercept(req: HttpRequest<any>, next: HttpHandler): Observable<HttpEvent<any>> {
    let clonedRequest = req;
    const token = localStorage.getItem("token");
    if (token) {
      // Clonar la solicitud y agregar el token de autenticación en el encabezado Authorization
      clonedRequest = req.clone({
        setHeaders: {
          Authorization: `Bearer ${token}`
        }
      });
    }
    // Pasar la solicitud al siguiente interceptor o al controlador final
    return next.handle(clonedRequest);
  }
}