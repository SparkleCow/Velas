import { Component, OnInit } from '@angular/core';
import { CandleRequestDto, IngredientRequestDto } from '../models/candle';
import { Router } from '@angular/router';
import { HttpClientService } from '../http-client.service';

@Component({
  selector: 'app-candle-form',
  templateUrl: './candle-form.component.html',
  styleUrl: './candle-form.component.css'
})


export class CandleFormComponent implements OnInit {
  candle: CandleRequestDto = {
    name: '',
    description: '',
    principalImage: '',
    stock: 0,
    category: null,
    images: [],
    ingredients: []
  }

  ingredient1:IngredientRequestDto = {
    name: '',
    amount: 0,
  }

  ingredient2:IngredientRequestDto = {
    name: '',
    amount: 0,
  }

  ingredient3:IngredientRequestDto = {
    name: '',
    amount: 0,
  }

  ingredient4:IngredientRequestDto = {
    name: '',
    amount: 0,
  }

  ingredientes: IngredientRequestDto[] = [];

  imagen1:string="";
  imagen2:string="";
  imagen3:string="";

  imagenes:string[] = [];

  constructor(private router: Router, private httpClient: HttpClientService) { }

  ngOnInit(): void {
    this.httpClient.validateAdminRole().subscribe(() => { }, error => {
      console.log("No autorizado");
      this.home();
    });
  }

  home() {
    this.router.navigate([""]);
  }

  enviar() {
    if(this.ingredient1.name!="" && this.ingredient1.amount>0){
      this.ingredientes.push(this.ingredient1);
    }if(this.ingredient2.name!="" && this.ingredient2.amount>0){
      this.ingredientes.push(this.ingredient2);
    }if(this.ingredient3.name!="" && this.ingredient3.amount>0){
      this.ingredientes.push(this.ingredient3);
    }if(this.ingredient4.name!="" && this.ingredient4.amount>0){
      this.ingredientes.push(this.ingredient4);
    }
    
    if(this.imagen1!=""){
      this.imagenes.push(this.imagen1)
    }if(this.imagen2!=""){
      this.imagenes.push(this.imagen2)
    }if(this.imagen3!=""){
      this.imagenes.push(this.imagen3)
    }

    this.candle.images = this.imagenes;
    this.candle.ingredients = this.ingredientes;
    this.httpClient.createCandle(this.candle).subscribe(vela=>{
      alert("Vela creada con id "+vela.id+" creada con exito");
    },error => {
      console.log(error);
    });
  }
}
