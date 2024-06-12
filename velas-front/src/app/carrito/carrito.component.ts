import { Component, OnInit } from '@angular/core';
import { HttpClientService } from '../http-client.service';
import { Router } from '@angular/router';
import { Carrito } from '../models/carrito';

@Component({
  selector: 'app-carrito',
  templateUrl: './carrito.component.html',
  styleUrl: './carrito.component.css'
})
export class CarritoComponent implements OnInit{
  carrito:Carrito={
    id:         0,
    candles:    [],
    totalPrice: 0
  }

  constructor(private httpClient:HttpClientService, private router:Router){}

  ngOnInit(): void {
    this.httpClient.getCarrito().subscribe(carrito => {
      this.carrito = carrito
      console.log(this.carrito)
    })   
  }
}
