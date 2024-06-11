import { Component, OnInit } from '@angular/core';
import { HttpClientService } from '../http-client.service';
import { CandleResponseDto } from '../models/candle';

@Component({
  selector: 'app-galeria',
  templateUrl: './galeria.component.html',
  styleUrl: './galeria.component.css'
})
export class GaleriaComponent implements OnInit{
  candles:CandleResponseDto[] = [];

  constructor(private httpClient: HttpClientService){}

  ngOnInit(): void {
    this.httpClient.findAllCandle().subscribe(
      data => {
        this.candles = data;
        console.log(this.candles);
      },
      error => {
        console.error('Error fetching candles:', error);
      }
    );
  }
}
