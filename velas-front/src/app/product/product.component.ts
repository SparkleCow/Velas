import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { HttpClientService } from '../http-client.service';
import { CandleResponseDto } from '../models/candle';

@Component({
  selector: 'app-product',
  templateUrl: './product.component.html',
  styleUrl: './product.component.css'
})
export class ProductComponent implements OnInit{
  
  id:number = 0;

  candle:CandleResponseDto = {
    id:0,
    name:"",
    description:"",
    principalImage:"",
    stock:0,
    category:null,
    images:[],
    price:0
  }

  constructor(private route: ActivatedRoute,private router:Router, private httpClient:HttpClientService) { }

  ngOnInit(): void {
    this.route.params.subscribe(param => this.id = param["id"])
    this.httpClient.findCandleById(this.id).subscribe(candle => this.candle = candle);
  }

  regresar(){
    this.router.navigate([""]);
  }

  carrito(){
    if(!localStorage.getItem("token")){
      this.router.navigate(["/login"])
      return;
    }
    this.router.navigate(["/carrito"])
  }
}
