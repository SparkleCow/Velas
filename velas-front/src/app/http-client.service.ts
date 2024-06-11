import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { CandleResponseDto } from './models/candle';

@Injectable({
  providedIn: 'root'
})
export class HttpClientService {
  private url:string = "http://localhost:8080/api/v1";

  constructor(private http:HttpClient) { }

  public findAllCandle():Observable<CandleResponseDto[]>{
    return this.http.get<CandleResponseDto[]>(`${this.url}/candle`);
  }
}
