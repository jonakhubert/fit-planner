import { HttpClient, HttpErrorResponse } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable, catchError, tap } from 'rxjs';
import { RegisterRequest } from '../interface/register-request';
import { AuthenticationResponse } from '../interface/authentication-response';
import { LoginRequest } from '../interface/login-request';
import { ApiError } from '../../interface/api-error';

@Injectable({
  providedIn: 'root'
})
export class AuthenticationService {

  private readonly apiUrl = 'http://localhost:8222/api/auth';

  constructor(private http: HttpClient) {}

  public register(request: RegisterRequest): Observable<AuthenticationResponse> {
    return this.http.post<AuthenticationResponse>(`${this.apiUrl}/register`, request)
      .pipe(
        catchError((error: HttpErrorResponse) => {
          throw this.buildApiError(error);
        }),
        tap((response: AuthenticationResponse) => {
          localStorage.setItem('userEmail', request.email);
          localStorage.setItem('token', response.access_token);
        })
      );
  }

  public login(request: LoginRequest): Observable<AuthenticationResponse> {
    return this.http.post<AuthenticationResponse>(`${this.apiUrl}/login`, request)
    .pipe(
      catchError((error: HttpErrorResponse) => {
        throw this.buildApiError(error);
      }),
      tap((response: AuthenticationResponse) => {
        localStorage.setItem('userEmail', request.email);
        localStorage.setItem('token', response.access_token);
      })
    );
  }

  private buildApiError(error: HttpErrorResponse) {
    const apiError: ApiError = {
      path: error.error.path,
      message: error.error.message,
      statusCode: error.error.statusCode,
      time: error.error.timestamp
    };

    return apiError;
  }
}