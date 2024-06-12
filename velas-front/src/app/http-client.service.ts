import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { CandleResponseDto } from './models/candle';
import { Token, UserLoginDto, UserRequestDto } from './models/user';
import { Carrito } from './models/carrito';

@Injectable({
  providedIn: 'root'
})
export class HttpClientService {
  private url:string = "http://localhost:8080/api/v1";

  constructor(private http:HttpClient) { }

  public findAllCandle():Observable<CandleResponseDto[]>{
    return this.http.get<CandleResponseDto[]>(`${this.url}/candle`);
  }

  public findCandleById(id:number):Observable<CandleResponseDto>{
    return this.http.get<CandleResponseDto>(`${this.url}/candle/${id}`)
  }

  public login(userLoginDto: UserLoginDto): Observable<Token> {
    return this.http.post<Token>(`${this.url}/auth/login`, userLoginDto);
  }

  public register(userRequestDto: UserRequestDto): Observable<void> {
    console.log(userRequestDto)
    return this.http.post<void>(`${this.url}/auth`, userRequestDto);
  }

  public validateToken(): Observable<boolean> {
    return this.http.get<boolean>(`${this.url}/auth/validate`);
  }

  public getCarrito(): Observable<Carrito>{
    return this.http.get<Carrito>(`${this.url}/car`);
  }
}
